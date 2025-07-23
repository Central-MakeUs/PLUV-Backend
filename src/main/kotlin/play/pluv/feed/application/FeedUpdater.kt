package play.pluv.feed.application

import org.springframework.stereotype.Component
import play.pluv.feed.domain.FeedBookmark
import play.pluv.feed.domain.repository.FeedBookmarkRepository
import play.pluv.feed.domain.repository.FeedRepository
import play.pluv.member.domain.Member

@Component
class FeedUpdater(
    private val feedRepository: FeedRepository,
    private val feedBookmarkRepository: FeedBookmarkRepository
) {

    fun bookmarkFeed(member: Member, feedId: Long) {
        val feed = feedRepository.readById(feedId)
        feedBookmarkRepository.save(FeedBookmark(feed = feed, memberId = member.identifier))
    }

    fun cancelBookmarkFeed(member: Member, feedId: Long) {
        feedBookmarkRepository.deleteByMemberIdAndFeedId(
            memberId = member.identifier,
            feedId = feedId
        )
    }
}
