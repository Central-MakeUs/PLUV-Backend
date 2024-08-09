package play.pluv.oauth.google;

import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import play.pluv.music.application.MusicExplorer;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.oauth.application.SocialLoginClient;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.oauth.google.dto.GoogleOAuthResponse;
import play.pluv.oauth.google.dto.YoutubeAddMusicRequest;
import play.pluv.oauth.google.dto.YoutubeCreatePlayListRequest;
import play.pluv.oauth.google.dto.YoutubeMusicResponses;
import play.pluv.playlist.application.PlayListConnector;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;

@Component
@RequiredArgsConstructor
public class GoogleConnector implements SocialLoginClient, PlayListConnector, MusicExplorer {

  private static final String AUTHORIZATION_FORMAT = "Bearer %s";
  private static final Function<String, String> CREATE_AUTH_HEADER
      = (token) -> String.format(AUTHORIZATION_FORMAT, token);

  private final GoogleApiClient googleApiClient;
  private final GoogleConfigProperty googleConfigProperty;

  @Override
  public OAuthMemberInfo fetchMember(final String idToken) {
    return googleApiClient.verifyIdToken(idToken)
        .toOAuthMemberInfo();
  }

  @Override
  public List<PlayList> getPlayList(final String accessToken) {
    return googleApiClient.getPlayList(CREATE_AUTH_HEADER.apply(accessToken))
        .toPlayLists();
  }

  @Override
  public List<PlayListMusic> getMusics(final String playListId, final String accessToken) {
    final List<PlayListMusic> result = new ArrayList<>();
    String nextPageToken = null;

    do {
      YoutubeMusicResponses playListItem = googleApiClient.getPlayListItems(
          CREATE_AUTH_HEADER.apply(accessToken), playListId, nextPageToken
      );
      result.addAll(playListItem.toPlayListMusics());
      nextPageToken = playListItem.nextPageToken();
    } while (nextPageToken != null);

    return result;
  }

  @Override
  public PlayListId createPlayList(final String accessToken, final String title) {
    return googleApiClient.createPlayList(
        CREATE_AUTH_HEADER.apply(accessToken), YoutubeCreatePlayListRequest.from(title)
    ).toPlayListId();
  }

  @Override
  public DestinationMusics searchMusic(
      final String accessToken, final PlayListMusic source
  ) {
    final String q = source.getTitle() + String.join(",", source.getArtistNames());
    return googleApiClient.searchMusic(CREATE_AUTH_HEADER.apply(accessToken), q)
        .toDestinationMusics();
  }

  @Override
  //todo : webflux로 전환, parrllelStream 쓰면 오류나는 이유 찾기
  public void transferMusics(
      final String accessToken, final List<MusicId> musicIds, final String playListName
  ) {
    final PlayListId playListId = createPlayList(accessToken, playListName);
    final String authorization = CREATE_AUTH_HEADER.apply(accessToken);
    musicIds.stream()
        .map(musicId -> YoutubeAddMusicRequest.of(playListId.id(), musicId))
        .forEach(request -> googleApiClient.addMusic(authorization, request));
  }

  @Override
  public MusicStreaming supportedType() {
    return YOUTUBE;
  }

  public GoogleOAuthResponse getAccessToken(final String authCode) {
    return googleApiClient.fetchToken(createRequestParam(authCode));
  }

  private MultiValueMap<String, String> createRequestParam(final String authCode) {
    final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    param.add("grant_type", "authorization_code");
    param.add("code", authCode);
    param.add("redirect_uri", googleConfigProperty.redirectUri());
    param.add("client_id", googleConfigProperty.clientId());
    param.add("client_secret", googleConfigProperty.clientSecret());
    return param;
  }
}
