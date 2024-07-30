package play.pluv.oauth.google;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import play.pluv.oauth.google.dto.GoogleIdTokenResponse;
import play.pluv.oauth.google.dto.GoogleOAuthResponse;
import play.pluv.oauth.google.dto.YoutubePlayListResponses;

public interface GoogleApiClient {

  @GetExchange("https://oauth2.googleapis.com/tokeninfo")
  GoogleIdTokenResponse verifyIdToken(@RequestParam("id_token") final String idToken);

  @PostExchange(url = "https://oauth2.googleapis.com/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
  GoogleOAuthResponse fetchToken(@RequestParam final MultiValueMap<String, String> params);

  @GetExchange("https://www.googleapis.com/youtube/v3/playlists?part=snippet&mine=true&maxResults=50")
  YoutubePlayListResponses getPlayList(@RequestHeader("Authorization") final String accessToken);
}
