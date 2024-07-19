package play.pluv.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
      final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution
  ) throws IOException {
    logging(request, body);
    final ClientHttpResponse response = execution.execute(request, body);
    logging(response);
    return response;
  }

  private void logging(final HttpRequest request, final byte[] body) {
    final StringBuilder sb = new StringBuilder();

    sb.append(String.format("%s %s \n", request.getMethod(), request.getURI()));

    request.getHeaders().forEach(
        (header, values) -> {
          sb.append(String.format("%s : %s \n", header, values));
        }
    );

    if (body.length > 0) {
      sb.append("\n");
      sb.append(new String(body));
    }

    log.info("Request : \n{}", sb);
  }

  private void logging(final ClientHttpResponse response) throws IOException {
    final StringBuilder sb = new StringBuilder();
    final InputStreamReader isr = new InputStreamReader(
        response.getBody(), StandardCharsets.UTF_8);
    final String body = new BufferedReader(isr).lines()
        .collect(Collectors.joining("\n"));

    sb.append(String.format("%s\n", response.getStatusCode()));
    sb.append("\n");
    sb.append(body);
    log.info("Response : \n{}", sb);
  }
}
