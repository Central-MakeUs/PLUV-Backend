package play.pluv.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

  @Bean
  public FilterRegistrationBean<LogFilter> myFilterRegistration() {
    final FilterRegistrationBean<LogFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new LogFilter());
    // 모든 URL에 필터 적용
    registration.addUrlPatterns(
        "/music/*", "/oauth/*", "/login/*", "/playlist/*"
    );
    registration.setOrder(1); // 필터 순서 지정 (optional)
    return registration;
  }
}
