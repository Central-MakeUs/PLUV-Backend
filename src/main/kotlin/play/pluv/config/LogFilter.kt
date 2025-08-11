package play.pluv.config

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.io.UnsupportedEncodingException

class LogFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest, response: ServletResponse, filterChain: FilterChain
    ) {
        val httpServletRequest = ContentCachingRequestWrapper(request as HttpServletRequest)
        val httpServletResponse = ContentCachingResponseWrapper(response as HttpServletResponse)

        filterChain.doFilter(httpServletRequest, httpServletResponse)

        loggingRequest(httpServletRequest)
        loggingResponse(httpServletResponse)
    }

    companion object {

        private val log = LoggerFactory.getLogger(LogFilter::class.java)

        @Throws(UnsupportedEncodingException::class)
        private fun loggingRequest(httpServletRequest: ContentCachingRequestWrapper) {
            val uri = httpServletRequest.requestURI

            //request 내용 확인
            val reqContent = String(
                bytes = httpServletRequest.contentAsByteArray,
                charset = charset(charsetName = httpServletRequest.characterEncoding)
            )

            try {
                MDC.put("uri", uri)
                MDC.put("contentBody", reqContent)
                log.info("request logging")
            } finally {
                MDC.clear()
            }
        }

        @Throws(IOException::class)
        private fun loggingResponse(
            httpServletResponse: ContentCachingResponseWrapper
        ) {
            val httpStatus = httpServletResponse.status
            val resContent = String(httpServletResponse.contentAsByteArray)

            //주의 : response를 클라이언트에서 볼 수 있도록 하려면 response를 복사해야 한다. response를 콘솔에 보여주면 내용이 사라진다.
            httpServletResponse.copyBodyToResponse()

            try {
                MDC.put("httpStatus", httpStatus.toString())
                MDC.put("contentBody", resContent)
                log.info("response logging")
            } finally {
                MDC.clear()
            }
        }
    }
}
