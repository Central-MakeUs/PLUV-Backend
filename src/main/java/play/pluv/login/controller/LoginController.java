package play.pluv.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.login.application.JwtProvider;
import play.pluv.login.application.LoginService;
import play.pluv.login.application.dto.LoginRequest;
import play.pluv.login.application.dto.LoginResponse;
import play.pluv.music.domain.MusicStreaming;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final JwtProvider jwtProvider;

  @PostMapping("/{oauth}/login")
  public BaseResponse<LoginResponse> login(
      @PathVariable final String oauth, @RequestBody final LoginRequest loginRequest
  ) {
    final var memberId = loginService
        .createToken(MusicStreaming.from(oauth), loginRequest.authCode());
    final var loginResponse = new LoginResponse(jwtProvider.createAccessTokenWith(memberId));
    return BaseResponse.ok(loginResponse);
  }
}
