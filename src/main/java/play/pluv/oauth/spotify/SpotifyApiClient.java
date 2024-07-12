package play.pluv.oauth.spotify;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface SpotifyApiClient {

  @GetExchange(url = "https://api.spotify.com/v1/me/playlists")
  SpotifyPlayListResponses getPlayList(@RequestHeader("Authorization") final String accessToken);

  @PostExchange(url = "https://accounts.spotify.com/api/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
  SpotifyAccessTokenResponse getAccessToken(@RequestParam final MultiValueMap<String, String> params);
}
