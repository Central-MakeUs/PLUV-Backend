package play.pluv.feed.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.FeedFixture.저장된_피드_1;
import static play.pluv.fixture.FeedFixture.저장된_피드_2;
import static play.pluv.fixture.MemberEntityFixture.멤버_홍혁준;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.feed.domain.Feed;
import play.pluv.feed.domain.repository.FeedRepository;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.support.ApplicationTest;

class FeedUpdaterTest extends ApplicationTest {

  @Autowired
  private FeedUpdater feedUpdater;
  @Autowired
  private FeedRepository feedRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private FeedReader feedReader;

  @Test
  void 피드를_유저가_저장한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final Feed feed1 = 저장된_피드_1(feedRepository);
    저장된_피드_2(feedRepository);

    feedUpdater.bookmarkFeed(member, feed1.getId());

    final List<Feed> actual = feedReader.findBookmarkedFeeds(member);
    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(feed1);
  }
}