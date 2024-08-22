package play.pluv.feed.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import play.pluv.feed.domain.Feed;
import play.pluv.feed.domain.FeedBookmark;
import play.pluv.feed.domain.repository.FeedBookmarkRepository;
import play.pluv.feed.domain.repository.FeedRepository;
import play.pluv.member.domain.Member;

@Component
@RequiredArgsConstructor
public class FeedUpdater {

  private final FeedRepository feedRepository;
  private final FeedBookmarkRepository feedBookmarkRepository;

  public void bookmarkFeed(final Member member, final Long feedId) {
    final Feed feed = feedRepository.readById(feedId);

    feedBookmarkRepository.save(new FeedBookmark(feed, member.getId()));
  }
}
