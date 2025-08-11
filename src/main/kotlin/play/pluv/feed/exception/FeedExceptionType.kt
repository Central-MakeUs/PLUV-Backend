package play.pluv.feed.exception

import org.springframework.http.HttpStatus
import play.pluv.base.BaseExceptionType

enum class FeedExceptionType(
    override val httpStatus: HttpStatus,
    override val message: String,
) : BaseExceptionType {
    FEED_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 feed를 찾을 수 없습니다");
}
