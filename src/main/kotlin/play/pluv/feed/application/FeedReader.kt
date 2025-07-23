package play.pluv.feed.application

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import play.pluv.feed.domain.Feed
import play.pluv.feed.domain.repository.FeedBookmarkRepository
import play.pluv.feed.domain.repository.FeedRepository
import play.pluv.member.domain.Member

@Component
@Transactional(readOnly = true)
class FeedReader(
    private val feedRepository: FeedRepository,
    private val feedBookmarkRepository: FeedBookmarkRepository
) {

    fun findAll(): List<Feed> {
        return feedRepository.findAll()
            .sortedByDescending { it.createdAt }
    }

    fun findBookmarkedFeeds(member: Member): List<Feed> {
        return feedBookmarkRepository.findByMemberIdWithJoin(member.identifier)
            .sortedByDescending { it.createdAt }
            .map { it.feed }
    }

    fun findFeed(feedId: Long): Feed {
        return feedRepository.readById(feedId)
    }

    fun isBookMarked(feed: Feed, memberId: Long): Boolean {
        return feedBookmarkRepository.existsByMemberIdAndFeed(memberId, feed)
    }
}
