package play.pluv.oauth.google;

import static play.pluv.music.domain.MusicStreaming.YOUTUBE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.oauth.application.SocialLoginClient;
import play.pluv.oauth.domain.OAuthMemberInfo;
import play.pluv.oauth.google.dto.GoogleOAuthResponse;
import play.pluv.oauth.google.dto.YoutubeMusicResponses;
import play.pluv.playlist.application.PlayListConnector;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;

@Component
@RequiredArgsConstructor
public class GoogleConnector implements SocialLoginClient, PlayListConnector {

  private static final String AUTHORIZATION_FORMAT = "Bearer %s";
  private static final Function<String, String> CREATE_AUTH_HEADER
      = (token) -> String.format(AUTHORIZATION_FORMAT, token);

  private final GoogleApiClient googleApiClient;
  private final GoogleConfigProperty googleConfigProperty;
  private final GenericResponseService responseBuilder;

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
  public PlayListId createPlayList(final String accessToken, final String name) {
    return null;
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
