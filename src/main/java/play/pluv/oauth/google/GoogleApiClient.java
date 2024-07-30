package play.pluv.oauth.google;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import play.pluv.oauth.google.dto.GoogleIdTokenResponse;

public interface GoogleApiClient {

  @GetExchange("https://oauth2.googleapis.com/tokeninfo")
  GoogleIdTokenResponse verifyIdToken(@RequestParam("id_token") final String idToken);
}
