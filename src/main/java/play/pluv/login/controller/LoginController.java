package play.pluv.login.controller;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.security.JwtProvider;
import play.pluv.login.application.LoginService;
import play.pluv.login.application.dto.AppleLoginRequest;
import play.pluv.login.application.dto.GoogleLoginRequest;
import play.pluv.security.JwtMemberId;
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

  @PostMapping("/login/google/add")
  public BaseResponse<String> addGoogleLoginWay(
      @Valid @RequestBody final GoogleLoginRequest loginRequest, final JwtMemberId jwtMemberId
  ) {
    loginService.addOtherLoginWay(YOUTUBE, jwtMemberId.memberId(), loginRequest.idToken());
    return BaseResponse.ok("");
  }

  @PostMapping("/login/spotify/add")
  public BaseResponse<String> addSpotifyLoginWay(
      @Valid @RequestBody final SpotifyLoginRequest loginRequest, final JwtMemberId jwtMemberId
  ) {
    loginService.addOtherLoginWay(SPOTIFY, jwtMemberId.memberId(), loginRequest.accessToken());
    return BaseResponse.ok("");
  }

  @PostMapping("/login/apple")
  public BaseResponse<LoginResponse> loginApple(
      @Valid @RequestBody final AppleLoginRequest loginRequest
  ) {
    final var memberId = loginService.createToken(APPLE, loginRequest.idToken());
    final var loginResponse = new LoginResponse(jwtProvider.createAccessTokenWith(memberId));
    return BaseResponse.ok(loginResponse);
  }

  @PostMapping("/login/apple/add")
  public BaseResponse<String> addAppleLoginWay(
      @Valid @RequestBody final AppleLoginRequest loginRequest, final JwtMemberId jwtMemberId
  ) {
    loginService.addOtherLoginWay(APPLE, jwtMemberId.memberId(), loginRequest.idToken());
    return BaseResponse.ok("");
  }
}
