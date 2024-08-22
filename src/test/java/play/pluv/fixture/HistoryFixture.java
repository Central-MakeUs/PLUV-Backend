package play.pluv.fixture;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.util.List;
import play.pluv.history.domain.History;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.history.domain.repository.TransferFailMusicRepository;

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

  public static History 저장된_히스토리_1(final HistoryRepository historyRepository, final Long memberId) {
    return historyRepository.save(히스토리_1(memberId));
  }

  public static History 저장된_히스토리_2(final Long memberId) {
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

  public static History 저장된_히스토리_2(final HistoryRepository historyRepository, final Long memberId) {
    return historyRepository.save(저장된_히스토리_2(memberId));
  }

  public static TransferFailMusic 이전실패한_음악1(
      final TransferFailMusicRepository repository, final Long historyId
  ) {
    return repository.save(
        TransferFailMusic.builder()
            .title("이전 실패한 음악_1")
            .historyId(historyId)
            .artistNames("이전실패음악_가수")
            .imageUrl("imageUrl")
            .build()
    );
  }

  public static TransferFailMusic 이전실패한_음악2(
      final TransferFailMusicRepository repository, final Long historyId
  ) {
    return repository.save(
        TransferFailMusic.builder()
            .title("이전 실패한 음악 2")
            .historyId(historyId)
            .artistNames("이전실패음악 가수2")
            .imageUrl("imageUrl")
            .build()
    );
  }

  public static List<TransferFailMusic> 저장된_이전실패한_음악들(
      final TransferFailMusicRepository repository, final Long historyId
  ) {
    return List.of(이전실패한_음악1(repository, historyId), 이전실패한_음악2(repository, historyId));
  }
}
