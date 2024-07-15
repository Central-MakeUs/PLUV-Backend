package play.pluv.oauth.spotify;

import static play.pluv.playlist.domain.PlayListProvider.SPOTIFY;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import play.pluv.playlist.application.MusicPlatformConnector;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListProvider;

@Component
@RequiredArgsConstructor
public class SpotifyConnector implements MusicPlatformConnector {

  private static final String AUTHORIZATION_FORMAT = "Bearer %s";

  private final SpotifyApiClient spotifyApiClient;
  private final SpotifyConfigProperty spotifyConfigProperty;

  @Override
  public List<PlayList> getPlayList(final String authCode) {
    final String accessToken = getAccessToken(authCode);
    final SpotifyPlayListResponses response = spotifyApiClient.getPlayList(
        String.format(AUTHORIZATION_FORMAT, accessToken)
    );
    return response.toPlayLists();
  }

  @Override
  public PlayListProvider supportedType() {
    return SPOTIFY;
  }

  private String getAccessToken(final String authCode) {
    return spotifyApiClient.getAccessToken(createAccessTokenRequestParam(authCode))
        .accessToken();
  }

  private MultiValueMap<String, String> createAccessTokenRequestParam(final String authCode) {
    final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    param.add("grant_type", "authorization_code");
    param.add("code", authCode);
    param.add("redirect_uri", spotifyConfigProperty.redirectUri());
    param.add("client_id", spotifyConfigProperty.clientId());
    param.add("client_secret", spotifyConfigProperty.clientSecret());
    return param;
  }
}
