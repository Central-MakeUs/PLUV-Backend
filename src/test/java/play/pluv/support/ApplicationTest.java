package play.pluv.support;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import play.pluv.config.DatabaseCleanerExtension;
import play.pluv.fixture.SpotifyFixture;
import play.pluv.oauth.spotify.SpotifyApiClient;
import play.pluv.support.ApplicationTest.MockBeanConfig;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(DatabaseCleanerExtension.class)
@Import(MockBeanConfig.class)
public abstract class ApplicationTest {

  @Configuration
  public static class MockBeanConfig {

    @Bean
    @Primary
    public SpotifyApiClient mockSpotifyApiClient() {
      final SpotifyApiClient spotifyApiClient = mock(SpotifyApiClient.class);
      SpotifyFixture.mockingClient(spotifyApiClient);
      return spotifyApiClient;
    }
  }
}
