package play.pluv.fixture;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.time.LocalDateTime;
import java.util.List;
import play.pluv.feed.application.dto.FeedDetailResponse;
import play.pluv.history.domain.History;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;

public class HistoryEntityFixture {

  public static History 히스토리_1(final Long memberId) {
    return new History(
        1L, "히스토리 1", "thumbNailurl", 7, 10, memberId, SPOTIFY, YOUTUBE, LocalDateTime.now(), 3L
    );
  }

  public static History 히스토리_2(final Long memberId) {
    return new History(
        1L, "히스토리 2", "thumbNailurl", 10, 10, memberId, APPLE, SPOTIFY, LocalDateTime.now(), 3L
    );
  }

  public static List<History> 히스토리들(final Long memberId) {
    return List.of(히스토리_1(memberId), 히스토리_2(memberId));
  }

  public static List<TransferFailMusic> 이전실패한_음악들(final Long historyId) {
    return List.of(
        new TransferFailMusic(historyId, "이전 실패한 음악 1", "imagerUrl", "이전 실패한 음악1의 가수 이름"),
        new TransferFailMusic(historyId, "이전 실패한 음악 2", "imagerUrl", "이전 실패한 음악2의 가수 이름")
    );
  }

  public static List<TransferredMusic> 이전한_음악들(final Long historyId) {
    return List.of(
        new TransferredMusic(historyId, "다시 사랑한다 말할까", "imagerUrl", "김동률",
            new HistoryMusicId(SPOTIFY, "ab")),
        new TransferredMusic(historyId, "오래된 노래", "imagerUrl", "김동률",
            new HistoryMusicId(SPOTIFY, "cd"))
    );
  }

}
