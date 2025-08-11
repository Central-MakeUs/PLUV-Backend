package play.pluv.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class FilterConfiguration {

    @Bean
    fun myFilterRegistration() = FilterRegistrationBean(LogFilter()).apply {
        addUrlPatterns("/music/*", "/oauth/*", "/login/*", "/playlist/*")
        order = Ordered.HIGHEST_PRECEDENCE
    }
}
