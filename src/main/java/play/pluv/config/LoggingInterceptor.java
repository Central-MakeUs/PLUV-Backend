package play.pluv.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
    MDC.put("method", request.getMethod().toString());
    MDC.put("uri", request.getURI().toString());
    loggingHeader(request);
    loggingBody(body);
    log.info("외부 api 요청");
    MDC.clear();
  }

  private static void loggingBody(final byte[] body) {
    final StringBuilder sb = new StringBuilder();
    if (body.length > 0) {
      sb.append("\n");
      sb.append(new String(body));
    }
    MDC.put("body", sb.toString());
  }

  private static void loggingHeader(final HttpRequest request) {
    final StringBuilder sb = new StringBuilder();
    request.getHeaders().forEach(
        (header, values) -> sb.append(String.format("%s : %s \n", header, values))
    );
    MDC.put("header", sb.toString());
  }

  private void logging(final ClientHttpResponse response) throws IOException {
    final InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);
    final String body = new BufferedReader(isr).lines()
        .collect(Collectors.joining("\n"));

    MDC.put("status", response.getStatusCode().toString());
    MDC.put("body", body);
    log.info("외부 api 응답");
    MDC.clear();
  }
}
