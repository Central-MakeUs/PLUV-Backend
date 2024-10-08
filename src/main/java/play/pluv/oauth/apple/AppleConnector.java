package play.pluv.oauth.apple;

import static io.jsonwebtoken.io.Decoders.BASE64;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static play.pluv.oauth.exception.OAuthExceptionType.GENERATE_APPLE_CLIENT_SECRET_ERROR;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.music.application.MusicExplorer;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.oauth.apple.dto.AppleAddMusicRequest;
import play.pluv.oauth.apple.dto.AppleCreatePlayListRequest;
import play.pluv.oauth.apple.dto.ApplePlayListResponses;
import play.pluv.oauth.apple.dto.AppleTokenResponse;
import play.pluv.oauth.application.SocialLoginClient;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.oauth.exception.OAuthException;
import play.pluv.playlist.application.PlayListConnector;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;
import play.pluv.progress.application.MusicTransferContextManager;

@Component
public class AppleConnector implements SocialLoginClient, PlayListConnector, MusicExplorer {

  private static final Integer MUSIC_ID_MAX_SIZE = 100;
  private static final String AUDIENCE = "https://appleid.apple.com";
  private static final Long EXP = MILLISECONDS.convert(30, MINUTES);

  private final String developerAuthorization;
  private final ObjectMapper objectMapper;
  private final AppleApiClient appleApiClient;
  private final AppleConfigProperty appleConfigProperty;
  private final MusicTransferContextManager musicTransferContextManager;

  public AppleConnector(
      final AppleConfigProperty appleConfigProperty, final AppleApiClient appleApiClient,
      final MusicTransferContextManager musicTransferContextManager,
      final ObjectMapper objectMapper
  ) {
    this.objectMapper = objectMapper;
    this.appleApiClient = appleApiClient;
    this.appleConfigProperty = appleConfigProperty;
    this.developerAuthorization = String.format("Bearer %s", appleConfigProperty.developerToken());
    this.musicTransferContextManager = musicTransferContextManager;
  }

  @Override
  public OAuthMemberInfo fetchMember(final String idToken) {
    final String userIdentifier = extractSub(idToken);
    return new OAuthMemberInfo(userIdentifier, APPLE);
  }

  public AppleTokenResponse geTokens(final String authCode) {
    final MultiValueMap<String, String> param = createRequestParamForAccessToken(authCode);
    return appleApiClient.fetchToken(param);
  }

  private String extractSub(final String idToken) {
    try {
      final String claimsBase64 = idToken.substring(idToken.indexOf('.') + 1,
          idToken.lastIndexOf('.'));
      final var decode = BASE64.decode(claimsBase64);
      final Map<String, Object> claims = objectMapper.readValue(decode, new TypeReference<>() {
      });
      return (String) claims.get("sub");
    } catch (final Exception exception) {
      throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
    }
  }

  @Override
  public List<PlayList> getPlayList(final String musicUserToken) {
    return appleApiClient.getPlayList(
        developerAuthorization, musicUserToken
    ).toPlayLists();
  }

  @Override
  public List<PlayListMusic> getMusics(final String playListId, final String musicUserToken) {
    return appleApiClient.getMusics(
        developerAuthorization, musicUserToken, playListId
    ).toPlayListMusics();
  }

  @Override
  public PlayListId createPlayList(final String musicUserToken, final String name) {
    final AppleCreatePlayListRequest request = AppleCreatePlayListRequest.from(name);
    appleApiClient.createPlayList(developerAuthorization, musicUserToken, request);
    try {
      Thread.sleep(1000 * 90);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    final ApplePlayListResponses playLists = appleApiClient.getPlayList(developerAuthorization,
        musicUserToken);
    return playLists.recentPlayListIds();
  }

  @Override
  public DestinationMusics searchMusic(final String musicUserToken, final PlayListMusic source) {
    return source.getIsrcCode()
        .map(isrc -> appleApiClient.searchMusicByIsrc(developerAuthorization, isrc)
            .toDestinationMusics()
        )
        .orElseGet(() -> searchByNameAndArtists(source));
  }

  private DestinationMusics searchByNameAndArtists(final PlayListMusic source) {
    final String term = source.getTitle() + source.getArtistNames();
    return appleApiClient.searchMusicByNameAndArtists(developerAuthorization, term)
        .toDestinationMusics();
  }

  @Override
  public void transferMusics(
      final Long memberId, final String musicUserToken, final List<MusicId> musicIds,
      final String playlistName
  ) {
    final PlayListId playListId = createPlayList(musicUserToken, playlistName);

    final List<List<MusicId>> requests = splitMusicIds(musicIds);

    requests.parallelStream()
        .forEach(request -> addMusics(memberId, musicUserToken, request, playListId));
    musicTransferContextManager.saveTransferHistory(memberId);
  }

  private void addMusics(
      final Long memberId, final String musicUserToken, final List<MusicId> musicIds,
      final PlayListId playlistId
  ) {
    final AppleAddMusicRequest request = AppleAddMusicRequest.of(musicIds);
    appleApiClient.addMusics(
        developerAuthorization, musicUserToken, playlistId.id(), request
    );
    final var historyMusicIds = musicIds.stream()
        .map(subMusicId -> new HistoryMusicId(subMusicId.musicStreaming(), subMusicId.id()))
        .toList();
    musicTransferContextManager.addTransferredMusics(memberId, historyMusicIds);
  }

  private List<List<MusicId>> splitMusicIds(final List<MusicId> musicIds) {
    final List<List<MusicId>> partitionedMusicIds = new ArrayList<>();
    for (int i = 0; i < musicIds.size(); i += MUSIC_ID_MAX_SIZE) {
      partitionedMusicIds.add(
          musicIds.subList(i, Math.min(i + MUSIC_ID_MAX_SIZE, musicIds.size()))
      );
    }
    return partitionedMusicIds;
  }

  @Override
  public MusicStreaming supportedType() {
    return APPLE;
  }

  private MultiValueMap<String, String> createRequestParamForAccessToken(final String authCode) {
    final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    param.add("grant_type", "authorization_code");
    param.add("code", authCode);
    param.add("redirect_uri", appleConfigProperty.redirectUri());
    param.add("client_id", appleConfigProperty.clientId());
    param.add("client_secret", generateClientSecret());
    return param;
  }

  private String generateClientSecret() {
    final Map<String, Object> jwtHeaders = Map.of(
        "kid", appleConfigProperty.keyId(),
        "alg", "ES256"
    );

    return Jwts.builder()
        .header().add(jwtHeaders).and()
        .issuer(appleConfigProperty.teamId())
        .issuedAt(new Date())
        .audience().add(AUDIENCE).and()
        .expiration(new Date(EXP + currentTimeMillis()))
        .subject(appleConfigProperty.clientId())
        .signWith(getPrivateKeyFromPem(appleConfigProperty.privateKey()))
        .compact();
  }

  private static ECPrivateKey getPrivateKeyFromPem(final String privateKey) {
    try {
      final byte[] decoded = Base64.getDecoder().decode(privateKey);
      final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
      final KeyFactory keyFactory = KeyFactory.getInstance("EC");
      return (ECPrivateKey) keyFactory.generatePrivate(keySpec);
    } catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new OAuthException(GENERATE_APPLE_CLIENT_SECRET_ERROR);
    }
  }
}
