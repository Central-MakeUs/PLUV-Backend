package play.pluv.login.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import play.pluv.base.BaseResponse
import play.pluv.login.application.LoginService
import play.pluv.login.application.dto.*
import play.pluv.playlist.domain.MusicStreaming.*
import play.pluv.security.JwtMemberId
import play.pluv.security.JwtProvider

@RestController
class LoginController(
    private val loginService: LoginService,
    private val jwtProvider: JwtProvider,
) {

    @PostMapping("/login/spotify")
    fun loginSpotify(@Valid @RequestBody loginRequest: SpotifyLoginRequest): BaseResponse<LoginResponse> {
        val memberId = loginService.registerAndGetMemberId(SPOTIFY, loginRequest.accessToken)
        val loginResponse = LoginResponse(jwtProvider.createAccessTokenWith(memberId))
        return BaseResponse.ok(loginResponse)
    }

    @PostMapping("/login/google")
    fun loginGoogle(@Valid @RequestBody loginRequest: GoogleLoginRequest): BaseResponse<LoginResponse> {
        val memberId = loginService.registerAndGetMemberId(YOUTUBE, loginRequest.idToken)
        val loginResponse = LoginResponse(jwtProvider.createAccessTokenWith(memberId))
        return BaseResponse.ok(loginResponse)
    }

    @PostMapping("/login/apple")
    fun loginApple(@Valid @RequestBody loginRequest: AppleLoginRequest): BaseResponse<LoginResponse> {
        val memberId = loginService.registerAndGetMemberId(APPLE, loginRequest.idToken)
        val loginResponse = LoginResponse(jwtProvider.createAccessTokenWith(memberId))
        return BaseResponse.ok(loginResponse)
    }

    @PostMapping("/login/tester")
    fun loginTester(@Valid @RequestBody loginRequest: TesterLoginRequest): BaseResponse<LoginResponse> {
        val memberId = loginService.getTesterId(loginRequest.id, loginRequest.password)
        val loginResponse = LoginResponse(jwtProvider.createAccessTokenWith(memberId))
        return BaseResponse.ok(loginResponse)
    }

    @GetMapping("/login/type")
    fun getLoginType(memberId: JwtMemberId): BaseResponse<List<LoginTypeResponse>> {
        val types = loginService.getLoginTypes(memberId.memberId)
        return BaseResponse.ok(LoginTypeResponse.createList(types))
    }

    @PostMapping("/login/apple/add")
    fun addAppleLoginWay(
        @Valid @RequestBody loginRequest: AppleLoginRequest,
        jwtMemberId: JwtMemberId
    ): BaseResponse<String> {
        loginService.addOtherLoginWay(APPLE, jwtMemberId.memberId, loginRequest.idToken)
        return BaseResponse.ok("")
    }

    @PostMapping("/login/spotify/add")
    fun addSpotifyLoginWay(
        @Valid @RequestBody loginRequest: SpotifyLoginRequest,
        jwtMemberId: JwtMemberId
    ): BaseResponse<String> {
        loginService.addOtherLoginWay(SPOTIFY, jwtMemberId.memberId, loginRequest.accessToken)
        return BaseResponse.ok("")
    }

    @PostMapping("/login/google/add")
    fun addAppleLoginWay(
        @Valid @RequestBody loginRequest: GoogleLoginRequest,
        jwtMemberId: JwtMemberId
    ): BaseResponse<String> {
        loginService.addOtherLoginWay(YOUTUBE, jwtMemberId.memberId, loginRequest.idToken)
        return BaseResponse.ok("")
    }
}