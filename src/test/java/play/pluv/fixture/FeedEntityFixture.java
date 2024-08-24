package play.pluv.fixture;

import java.util.List;
import play.pluv.feed.domain.Feed;

public class FeedEntityFixture {

  public static List<Feed> 피드목록() {
    return List.of(피드_1(), 피드_2());
  }

  private static Feed 피드_1() {
    return new Feed(
        1L, 2L, "여유로운 오후의 어쩌구 플레이리스트", "플러버", "가수 이름, 가수 이름, 가수 이름", "imageUrl", 10
    );
  }

  private static Feed 피드_2() {
    return new Feed(
        2L, 3L, "여유로운 오후", "홍실", "가수 이름, 가수 이름, 가수 이름", "imageUrl", 10
    );
  }
}
