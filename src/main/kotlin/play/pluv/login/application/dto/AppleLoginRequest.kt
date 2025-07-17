package play.pluv.login.application.dto

import jakarta.validation.constraints.NotBlank

data class AppleLoginRequest(val idToken: @NotBlank String)
