package play.pluv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import play.pluv.oauth.spotify.SpotifyApiClient;

@Configuration
public class RestClientConfig {

  private final LoggingInterceptor loggingInterceptor;

  public RestClientConfig(final LoggingInterceptor loggingInterceptor) {
    this.loggingInterceptor = loggingInterceptor;
  }

  @Bean
  public SpotifyApiClient spotifyApiClient() {
    return createHttpInterface(SpotifyApiClient.class);
  }

  private <T> T createHttpInterface(final Class<T> clazz) {
    final RestClient restClient = RestClient.builder()
        .requestInterceptor(loggingInterceptor)
        .requestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
        .build();
    final RestClientAdapter adapter = RestClientAdapter.create(restClient);
    final HttpServiceProxyFactory factory = HttpServiceProxyFactory
        .builderFor(adapter)
        .build();
    return factory.createClient(clazz);
  }
}
