package play.pluv.login.application.dto;

import jakarta.validation.constraints.NotBlank;

public record AppleLoginRequest(@NotBlank String idToken) {

}
