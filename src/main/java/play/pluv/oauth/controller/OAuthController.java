package play.pluv.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.oauth.application.OAuthService;
import play.pluv.oauth.application.dto.GoogleAccessToken;

@RestController
@RequiredArgsConstructor
public class OAuthController {

  private final OAuthService oauthService;

  @GetMapping("/youtube/oauth/token")
  public BaseResponse<GoogleAccessToken> getToken(@RequestParam final String code) {
    final var accessToken = oauthService.getAccessToken(code);
    return BaseResponse.ok(accessToken);
  }
}
