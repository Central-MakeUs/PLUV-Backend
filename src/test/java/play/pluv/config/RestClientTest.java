package play.pluv.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.fake.FakeRestClient;
import play.pluv.fake.FakeRestClient.HealthCheckResponse;
import play.pluv.support.ApplicationTest;

public class RestClientTest extends ApplicationTest {

  @Autowired
  private FakeRestClient fakeRestClient;

  @Test
  @Disabled
  void FakeRestClient는_정상적으로_요청을_보낸다() {
    final HealthCheckResponse healthCheck = fakeRestClient.getHealthCheck();

    assertAll(
        () -> assertThat(healthCheck.id()).isNotNull(),
        () -> assertThat(healthCheck.userId()).isNotNull(),
        () -> assertThat(healthCheck.title()).isNotNull(),
        () -> assertThat(healthCheck.body()).isNotNull()
    );
  }
}
