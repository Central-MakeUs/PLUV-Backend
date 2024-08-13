package play.pluv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import play.pluv.oauth.apple.AppleApiClient;
import play.pluv.oauth.google.GoogleApiClient;
import play.pluv.oauth.spotify.SpotifyApiClient;

@Configuration
public class RestClientConfig {

  private final LoggingInterceptor loggingInterceptor;
  private final Boolean logActive;

  public RestClientConfig(
      final LoggingInterceptor loggingInterceptor,
      @Value("${restClient.logging}") final boolean logActive
  ) {
    this.loggingInterceptor = loggingInterceptor;
    this.logActive = logActive;
  }

  @Bean
  public SpotifyApiClient spotifyApiClient() {
    return createHttpInterface(SpotifyApiClient.class);
  }

  @Bean
  public AppleApiClient appleApiClient() {
    return createHttpInterface(AppleApiClient.class);
  }

  @Bean
  public GoogleApiClient googleApiClient() {
    return createHttpInterface(GoogleApiClient.class);
  }

  private <T> T createHttpInterface(final Class<T> clazz) {
    Builder builder = RestClient.builder();
    if (logActive) {
      builder = addLogInterceptor(builder);
    }
    final RestClient restClient = builder.build();
    final RestClientAdapter adapter = RestClientAdapter.create(restClient);
    final HttpServiceProxyFactory factory = HttpServiceProxyFactory
        .builderFor(adapter)
        .build();
    return factory.createClient(clazz);
  }

  private Builder addLogInterceptor(Builder builder) {
    builder = builder
        .requestInterceptor(loggingInterceptor)
        .requestFactory(
            new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory())
        );
    return builder;
  }
}
