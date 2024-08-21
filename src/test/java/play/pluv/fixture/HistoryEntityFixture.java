package play.pluv.fixture;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.time.LocalDateTime;
import java.util.List;
import play.pluv.history.domain.History;

public class HistoryEntityFixture {

  public static History 히스토리_1(final Long memberId) {
    return new History(
        1L, "히스토리 1", "thumbNailurl", 7, 10, memberId, SPOTIFY, YOUTUBE, LocalDateTime.now()
    );
  }

  public static History 히스토리_2(final Long memberId) {
    return new History(
        1L, "히스토리 2", "thumbNailurl", 10, 10, memberId, APPLE, SPOTIFY, LocalDateTime.now()
    );
  }

  public static List<History> 히스토리들(final Long memberId) {
    return List.of(히스토리_1(memberId), 히스토리_2(memberId));
  }
}
