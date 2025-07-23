package play.pluv.playlist.application.dto

import jakarta.validation.constraints.NotBlank

data class ApplePlayListMusicReadRequest(
    val musicUserToken: @NotBlank String
)
