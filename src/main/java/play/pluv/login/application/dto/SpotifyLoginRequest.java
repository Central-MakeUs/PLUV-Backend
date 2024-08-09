package play.pluv.login.application.dto;

import jakarta.validation.constraints.NotBlank;

public record SpotifyLoginRequest(
    @NotBlank
    String accessToken
) {

}
