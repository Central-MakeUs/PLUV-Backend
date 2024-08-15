package play.pluv.oauth.apple;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "apple")
public record AppleConfigProperty(
    String keyId,
    String teamId,
    String clientId,
    String redirectUri,
    String privateKey,
    String developerToken
) {

}
