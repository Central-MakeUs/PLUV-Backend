package play.pluv.login.application.dto

import jakarta.validation.constraints.NotBlank

data class GoogleLoginRequest(
    val idToken: @NotBlank String
)
