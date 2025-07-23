package play.pluv.playlist.exception

import org.springframework.http.HttpStatus
import play.pluv.base.BaseExceptionType

enum class PlayListExceptionType(
    override val httpStatus: HttpStatus,
    override val message: String
) : BaseExceptionType {

    PLAYLIST_PROVIDER_NOT_FOUND(HttpStatus.NOT_FOUND, "지원하지 않는 스트리밍 서비스입니다");

}
