package play.pluv.oauth.spotify;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spotify")
public record SpotifyConfigProperty(
    String clientId,
    String clientSecret,
    String redirectUri
) {

}
