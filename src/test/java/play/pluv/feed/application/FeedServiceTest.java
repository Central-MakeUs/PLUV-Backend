package play.pluv.feed.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.FeedFixture.저장된_피드_1;
import static play.pluv.fixture.FeedFixture.저장된_피드_2;
import static play.pluv.fixture.MemberEntityFixture.멤버_홍혁준;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.feed.application.dto.FeedDetailResponse;
import play.pluv.feed.domain.Feed;
import play.pluv.feed.domain.repository.FeedRepository;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.support.ApplicationTest;

class FeedServiceTest extends ApplicationTest {

  @Autowired
  private FeedService feedService;
  @Autowired
  private FeedRepository feedRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private FeedReader feedReader;
  @Autowired
  private FeedUpdater feedUpdater;

  @Test
  void 피드목록을_조회한다() {
    final Feed savedFeed1 = 저장된_피드_1(feedRepository);
    final Feed savedFeed2 = 저장된_피드_2(feedRepository);

    final List<Feed> actual = feedService.findAll();

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(savedFeed1, savedFeed2);
  }

  @Test
  void 피드를_단건_조회한다() {
    final Feed feed = 저장된_피드_1(feedRepository);

    final FeedDetailResponse actual = feedService.findFeed(feed.getId(), 1L);
    final FeedDetailResponse expected = new FeedDetailResponse(
        feed.getId(), feed.getSongCount(), feed.getTitle(), feed.getThumbNailUrl(),
        feed.getCreatorName(), false, feed.getCreatedAt().toLocalDate()
    );

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void 피드를_북마크한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final Feed savedFeed1 = 저장된_피드_1(feedRepository);
    저장된_피드_2(feedRepository);

    feedService.bookmarkFeed(member.getId(), savedFeed1.getId());

    final List<Feed> actual = feedReader.findBookmarkedFeeds(member);

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(savedFeed1);
  }

  @Test
  void 북마크된_피드를_취소한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final Feed savedFeed1 = 저장된_피드_1(feedRepository);

    feedService.cancelBookmark(member.getId(), savedFeed1.getId());

    final List<Feed> actual = feedReader.findBookmarkedFeeds(member);

    assertThat(actual)
        .isEmpty();
  }

  @Test
  void 북마크된_피드를_조회한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final Feed savedFeed1 = 저장된_피드_1(feedRepository);
    저장된_피드_2(feedRepository);
    feedUpdater.bookmarkFeed(member, savedFeed1.getId());

    final List<Feed> actual = feedReader.findBookmarkedFeeds(member);

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(savedFeed1);
  }
}