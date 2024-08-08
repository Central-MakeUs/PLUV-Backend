package play.pluv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "localDateTimeProvider")
@ConfigurationPropertiesScan
public class PluvApplication {

  public static void main(String[] args) {
    SpringApplication.run(PluvApplication.class, args);
  }

}