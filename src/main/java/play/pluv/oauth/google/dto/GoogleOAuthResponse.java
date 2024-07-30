package play.pluv.oauth.google.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record GoogleOAuthResponse(
    String accessToken,
    String idToken
) {

}
