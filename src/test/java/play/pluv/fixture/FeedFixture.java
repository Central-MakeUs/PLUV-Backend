package play.pluv.fixture;

import play.pluv.feed.domain.Feed;
import play.pluv.feed.domain.repository.FeedRepository;

public class FeedFixture {

  public static Feed 저장된_피드_1(final FeedRepository feedRepository) {
    final Feed feed = new Feed(
        2L, 3L, "여유로운 오후의 어쩌구 플레이리스트", "플러버", "가수 이름, 가수 이름, 가수 이름", "imageUrl", true
    );
    return feedRepository.save(feed);
  }

  public static Feed 저장된_피드_2(final FeedRepository feedRepository) {
    final Feed feed = new Feed(
        3L, 4L, "여유로운 오후", "홍실", "가수 이름, 가수 이름, 가수 이름", "imageUrl", true
    );
    return feedRepository.save(feed);
  }
}
