package play.pluv.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import play.pluv.security.MemberIdArgumentResolver

@Configuration
class ArgumentResolverConfig(
    private val memberIdArgumentResolver: MemberIdArgumentResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(memberIdArgumentResolver)
    }
}
