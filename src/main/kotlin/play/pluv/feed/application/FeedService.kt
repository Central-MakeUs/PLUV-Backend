package play.pluv.feed.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import play.pluv.feed.application.dto.FeedDetailResponse
import play.pluv.feed.domain.Feed
import play.pluv.member.application.MemberReader

@Service
class FeedService(
    private val feedReader: FeedReader,
    private val feedUpdater: FeedUpdater,
    private val memberReader: MemberReader,
) {

    @Transactional(readOnly = true)
    fun findAll(): List<Feed> {
        return feedReader.findAll()
    }

    @Transactional
    fun bookmarkFeed(memberId: Long, feedId: Long) {
        val member = memberReader.readById(memberId)
        feedUpdater.bookmarkFeed(member = member, feedId = feedId)
    }

    @Transactional(readOnly = true)
    fun findBookmarkedFeeds(memberId: Long?): List<Feed> {
        val member = memberReader.readById(memberId)
        return feedReader.findBookmarkedFeeds(member = member)
    }

    @Transactional(readOnly = true)
    fun findFeed(id: Long, memberId: Long): FeedDetailResponse {
        val feed = feedReader.findFeed(feedId = id)
        val isBookmarked = feedReader.isBookMarked(feed = feed, memberId = memberId)
        return FeedDetailResponse.from(feed = feed, isBookMarked = isBookmarked)
    }

    @Transactional
    fun cancelBookmark(memberId: Long, feedId: Long) {
        val member = memberReader.readById(memberId)
        feedUpdater.cancelBookmarkFeed(member = member, feedId = feedId)
    }
}
