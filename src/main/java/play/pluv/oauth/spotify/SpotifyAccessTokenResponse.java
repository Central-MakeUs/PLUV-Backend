package play.pluv.oauth.spotify;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record SpotifyAccessTokenResponse(
    String accessToken
) {

}
