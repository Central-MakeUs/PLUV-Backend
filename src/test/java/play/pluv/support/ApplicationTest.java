package play.pluv.support;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import play.pluv.fixture.SpotifyFixture;
import play.pluv.oauth.spotify.SpotifyApiClient;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
public abstract class ApplicationTest {

  @Configuration
  public static class mockBeanConfig {

    @Bean
    @Primary
    public SpotifyApiClient mockSpotifyApiClient() {
      final SpotifyApiClient spotifyApiClient = mock(SpotifyApiClient.class);
      SpotifyFixture.mockingClient(spotifyApiClient);
      return spotifyApiClient;
    }
  }
}
