package play.pluv.playlist.application.dto

import jakarta.validation.constraints.NotBlank

data class ApplePlayListReadRequest(val musicUserToken: @NotBlank String)
