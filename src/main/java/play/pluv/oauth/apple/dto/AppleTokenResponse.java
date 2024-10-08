package play.pluv.oauth.apple.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record AppleTokenResponse(
    String accessToken,
    String idToken
) {

}
