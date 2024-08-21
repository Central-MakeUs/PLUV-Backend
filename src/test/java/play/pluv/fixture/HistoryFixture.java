package play.pluv.fixture;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import play.pluv.history.domain.History;
import play.pluv.history.domain.repository.HistoryRepository;

public class HistoryFixture {

  public static History 히스토리_1(final Long memberId) {
    return History.builder()
        .memberId(memberId)
        .title("히스토리 1")
        .source(SPOTIFY)
        .destination(APPLE)
        .transferredSongCount(10)
        .totalSongCount(15)
        .thumbNailUrl("thumbNailUrl")
        .build();
  }

  public static History 히스토리_1(final HistoryRepository historyRepository, final Long memberId) {
    return historyRepository.save(히스토리_1(memberId));
  }

  public static History 히스토리_2(final Long memberId) {
    return History.builder()
        .memberId(memberId)
        .title("히스토리 2")
        .source(YOUTUBE)
        .destination(SPOTIFY)
        .transferredSongCount(10)
        .totalSongCount(10)
        .thumbNailUrl("thumbNailUrl")
        .build();
  }

  public static History 히스토리_2(final HistoryRepository historyRepository, final Long memberId) {
    return historyRepository.save(히스토리_2(memberId));
  }
}
