package play.pluv.feed.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.feed.application.dto.FeedDetailResponse;
import play.pluv.feed.domain.Feed;
import play.pluv.member.application.MemberReader;
import play.pluv.member.domain.Member;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final FeedReader feedReader;
  private final FeedUpdater feedUpdater;
  private final MemberReader memberReader;

  @Transactional(readOnly = true)
  public List<Feed> findAll() {
    return feedReader.findAll();
  }

  @Transactional
  public void bookmarkFeed(final Long memberId, final Long feedId) {
    final Member member = memberReader.readById(memberId);
    feedUpdater.bookmarkFeed(member, feedId);
  }

  @Transactional(readOnly = true)
  public List<Feed> findBookmarkedFeeds(final Long memberId) {
    final Member member = memberReader.readById(memberId);
    return feedReader.findBookmarkedFeeds(member);
  }

  @Transactional(readOnly = true)
  public FeedDetailResponse findFeed(final Long id, final Long memberId) {
    final Feed feed = feedReader.findFeed(id);
    final Boolean isBookmarked = feedReader.isBookMarked(feed, memberId);
    return FeedDetailResponse.from(feed, isBookmarked);
  }

  @Transactional
  public void cancelBookmark(final Long aLong, final Long id) {

  }
}
