package play.pluv.feed.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import play.pluv.feed.domain.Feed
import play.pluv.feed.domain.FeedBookmark

interface FeedBookmarkRepository : JpaRepository<FeedBookmark, Long> {

    @Query(
        """
    select fb
    from FeedBookmark fb
    join fetch fb.feed
    where fb.memberId = :memberId
    """
    )
    fun findByMemberIdWithJoin(memberId: Long): List<FeedBookmark>

    fun existsByMemberIdAndFeed(memberId: Long, feed: Feed): Boolean

    fun deleteByMemberIdAndFeedId(memberId: Long, feedId: Long)
}
