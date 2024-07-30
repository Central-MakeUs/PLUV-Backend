package play.pluv.oauth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google")
public record GoogleConfigProperty(
    String clientId,
    String clientSecret,
    String redirectUri
) {

}
