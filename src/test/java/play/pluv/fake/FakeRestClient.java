package play.pluv.fake;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import org.springframework.web.service.annotation.GetExchange;

public interface FakeRestClient {

  @GetExchange(url = "https://jsonplaceholder.typicode.com/posts/1", accept = APPLICATION_FORM_URLENCODED_VALUE)
  HealthCheckResponse getHealthCheck();

  record HealthCheckResponse(
      Long userId, Long id, String title, String body
  ) {

  }
}
