package play.pluv.oauth.google;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import play.pluv.oauth.google.dto.GoogleIdTokenResponse;

public interface GoogleApiClient {

  @GetMapping("https://oauth2.googleapis.com/tokeninfo")
  GoogleIdTokenResponse verifyIdToken(@RequestParam("code") final String id_token);
}
