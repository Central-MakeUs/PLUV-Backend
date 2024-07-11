package play.pluv.support;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import play.pluv.fake.FakeRestClient;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
public abstract class ApplicationTest {

  //TODO: RestClientTest로 코드 넘기기
  @Configuration
  public static class TestRestClientConfig {

    @Bean
    public FakeRestClient fakeRestClient() {
      return createHttpInterface(FakeRestClient.class);
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
}
