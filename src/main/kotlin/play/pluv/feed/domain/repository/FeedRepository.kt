package play.pluv.feed.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import play.pluv.feed.domain.Feed
import play.pluv.feed.exception.FeedException
import play.pluv.feed.exception.FeedExceptionType.FEED_NOT_FOUND

interface FeedRepository : JpaRepository<Feed, Long> {
    fun readById(id: Long): Feed =
        findById(id).orElseThrow { FeedException(FEED_NOT_FOUND) }
}
