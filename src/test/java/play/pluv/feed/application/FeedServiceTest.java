package play.pluv.feed.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.FeedFixture.저장된_피드_1;
import static play.pluv.fixture.FeedFixture.저장된_피드_2;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.feed.domain.Feed;
import play.pluv.feed.domain.repository.FeedRepository;
import play.pluv.support.ApplicationTest;

class FeedServiceTest extends ApplicationTest {

  @Autowired
  private FeedService feedService;
  @Autowired
  private FeedRepository feedRepository;

  @Test
  void 피드목록을_조회한다() {
    final Feed savedFeed1 = 저장된_피드_1(feedRepository);
    final Feed savedFeed2 = 저장된_피드_2(feedRepository);

    final List<Feed> actual = feedService.findAll();

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(savedFeed1, savedFeed2);
  }
}