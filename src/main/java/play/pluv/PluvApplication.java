package play.pluv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PluvApplication {

  public static void main(String[] args) {
    SpringApplication.run(PluvApplication.class, args);
  }

}