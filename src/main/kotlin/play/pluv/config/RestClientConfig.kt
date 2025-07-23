package play.pluv.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import play.pluv.oauth.apple.AppleApiClient
import play.pluv.oauth.google.GoogleApiClient
import play.pluv.oauth.spotify.SpotifyApiClient
import play.pluv.playlist.infra.OcrApiClient

@Configuration
class RestClientConfig(
    private val restClientLoggingInterceptor: RestClientLoggingInterceptor,
    @param:Value($$"${restClient.logging}") private val logActive: Boolean
) {

    @Bean
    fun spotifyApiClient(): SpotifyApiClient {
        return createHttpInterface(clazz=SpotifyApiClient::class.java)
    }

    @Bean
    fun appleApiClient(): AppleApiClient {
        return createHttpInterface(AppleApiClient::class.java)
    }

    @Bean
    fun googleApiClient(): GoogleApiClient {
        return createHttpInterface(GoogleApiClient::class.java)
    }

    @Bean
    fun ocrApiClient(): OcrApiClient {
        return createHttpInterface(OcrApiClient::class.java)
    }

    private fun <T> createHttpInterface(clazz: Class<T>): T {
        var builder = RestClient.builder()
        if (logActive) {
            builder = addLogInterceptor(builder)
        }
        val restClient = builder.build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory
            .builderFor(adapter)
            .build()
        return factory.createClient<T>(clazz)
    }

    private fun addLogInterceptor(builder: RestClient.Builder): RestClient.Builder =
        builder
            .requestInterceptor(restClientLoggingInterceptor)
            .requestFactory(BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory()))
}
