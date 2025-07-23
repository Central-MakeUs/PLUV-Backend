package play.pluv.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.charset.StandardCharsets.UTF_8

@Component
class RestClientLoggingInterceptor : ClientHttpRequestInterceptor {

    companion object {
        private val log = LoggerFactory.getLogger(RestClientLoggingInterceptor::class.java)
    }

    @Throws(IOException::class)
    override fun intercept(
        request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        logRequest(request, body)
        val response = execution.execute(request, body)
        logResponse(response)
        return response
    }

    private fun logRequest(request: HttpRequest, body: ByteArray) {
        val sb = buildString {
            appendLine("${request.method} ${request.uri}")

            request.headers.forEach { (header, values) ->
                appendLine("$header : $values")
            }

            if (body.isNotEmpty()) {
                appendLine()
                appendLine(body.toString(UTF_8))
            }
        }

        log.info("Request:\n{}", sb)
    }

    @Throws(IOException::class)
    private fun logResponse(response: ClientHttpResponse) {
        val status = response.statusCode
        val bodyText = response.body.bufferedReader(UTF_8).use { it.readText() }

        val sb = buildString {
            appendLine(status.toString())
            appendLine()
            appendLine(bodyText)
        }

        log.info("Response:\n{}", sb)
    }
}
