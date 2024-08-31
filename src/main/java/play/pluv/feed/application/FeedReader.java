package play.pluv.feed.application;

import static java.util.Comparator.comparing;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.feed.domain.Feed;
import play.pluv.feed.domain.FeedBookmark;
import play.pluv.feed.domain.repository.FeedBookmarkRepository;
import play.pluv.feed.domain.repository.FeedRepository;
import play.pluv.member.domain.Member;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedReader {

  private final FeedRepository feedRepository;
  private final FeedBookmarkRepository feedBookmarkRepository;

  public List<Feed> findAll() {
    return feedRepository.findAll().stream()
        .sorted(comparing(Feed::getCreatedAt).reversed())
        .sorted(comparing(feed -> feed.getId() <= 116))
        .toList();
  }

  public List<Feed> findBookmarkedFeeds(final Member member) {
    return feedBookmarkRepository.findByMemberIdWithJoin(member.getId()).stream()
        .map(FeedBookmark::getFeed)
        .toList();
  }

  public Feed findFeed(final Long feedId) {
    return feedRepository.readById(feedId);
  }

  public Boolean isBookMarked(final Feed feed, final Long memberId) {
    return feedBookmarkRepository.existsByMemberIdAndFeed(memberId, feed);
  }
}
