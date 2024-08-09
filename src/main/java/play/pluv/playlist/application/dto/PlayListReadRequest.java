package play.pluv.playlist.application.dto;

import jakarta.validation.constraints.NotBlank;

public record PlayListReadRequest(
    @NotBlank String accessToken
) {
}
