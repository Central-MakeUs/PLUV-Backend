package play.pluv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import play.pluv.oauth.spotify.SpotifyApiClient;

@Configuration
public class RestClientConfig {

  @Bean
  public SpotifyApiClient spotifyApiClient() {
    return createHttpInterface(SpotifyApiClient.class);
  }

  private <T> T createHttpInterface(final Class<T> clazz) {
    final RestClient restClient = RestClient.create();
    final RestClientAdapter adapter = RestClientAdapter.create(restClient);
    final HttpServiceProxyFactory factory = HttpServiceProxyFactory
        .builderFor(adapter)
        .build();
    return factory.createClient(clazz);
  }
}
