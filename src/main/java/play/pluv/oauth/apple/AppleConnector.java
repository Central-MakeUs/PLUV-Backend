package play.pluv.oauth.apple;

import static io.jsonwebtoken.io.Decoders.BASE64;
import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static play.pluv.login.exception.LoginExceptionType.GENERATE_APPLE_CLIENT_SECRET_ERROR;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import play.pluv.login.exception.LoginException;
import play.pluv.oauth.apple.dto.AppleTokenResponse;
import play.pluv.oauth.application.SocialLoginClient;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.playlist.application.PlayListConnector;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;

@Component
@RequiredArgsConstructor
public class AppleConnector implements SocialLoginClient, PlayListConnector {

  private static final String AUDIENCE = "https://appleid.apple.com";
  private static final Long EXP = MILLISECONDS.convert(30, MINUTES);
  private static final String AUTHORIZATION_FORMAT = "Bearer %s";
  private static final Function<String, String> CREATE_AUTH_HEADER
      = (token) -> String.format(AUTHORIZATION_FORMAT, token);

  private final ObjectMapper objectMapper;
  private final AppleApiClient appleApiClient;
  private final AppleConfigProperty appleConfigProperty;

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
        CREATE_AUTH_HEADER.apply(appleConfigProperty.developerToken())
        , musicUserToken
    ).toPlayLists();
  }

  @Override
  public List<PlayListMusic> getMusics(final String playListId, final String accessToken) {
    return List.of();
  }

  @Override
  public PlayListId createPlayList(final String accessToken, final String name) {
    return null;
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
      throw new LoginException(GENERATE_APPLE_CLIENT_SECRET_ERROR);
    }
  }
}
