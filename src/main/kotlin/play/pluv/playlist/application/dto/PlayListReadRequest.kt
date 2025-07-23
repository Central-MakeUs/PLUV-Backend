package play.pluv.playlist.application.dto

import jakarta.validation.constraints.NotBlank

data class PlayListReadRequest(
  val accessToken: @NotBlank String
)
