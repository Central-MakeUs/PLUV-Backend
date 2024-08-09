package play.pluv.login.controller;

import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.login.application.JwtProvider;
import play.pluv.login.application.LoginService;
import play.pluv.login.application.dto.GoogleLoginRequest;
import play.pluv.login.application.dto.LoginResponse;
import play.pluv.login.application.dto.SpotifyLoginRequest;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final JwtProvider jwtProvider;

  @PostMapping("/login/spotify")
  public BaseResponse<LoginResponse> loginSpotify(
      @Valid @RequestBody final SpotifyLoginRequest loginRequest
  ) {
    final var memberId = loginService.createToken(SPOTIFY, loginRequest.accessToken());
    final var loginResponse = new LoginResponse(jwtProvider.createAccessTokenWith(memberId));
    return BaseResponse.ok(loginResponse);
  }

  @PostMapping("/login/google")
  public BaseResponse<LoginResponse> loginGoogle(
      @Valid @RequestBody final GoogleLoginRequest loginRequest
  ) {
    final var memberId = loginService.createToken(YOUTUBE, loginRequest.idToken());
    final var loginResponse = new LoginResponse(jwtProvider.createAccessTokenWith(memberId));
    return BaseResponse.ok(loginResponse);
  }
}
