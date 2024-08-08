package play.pluv.history.application;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.domain.History;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.member.domain.Member;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.playlist.domain.PlayListMusic;

@Component
public class HistoryUpdater {

  private final HistoryRepository historyRepository;

  public HistoryUpdater(final HistoryRepository historyRepository) {
    this.historyRepository = historyRepository;
  }

  @Transactional
  public Long createHistory(
      final String title, final String thumbNailUrl, final Member member,
      final List<PlayListMusic> cantTransferredMusics, final int canTransferSongCount
  ) {
    final History history = History.builder()
        .transferFailSongCount(cantTransferredMusics.size())
        .thumbNailUrl(thumbNailUrl)
        .memberId(member.getId())
        .transferredSongCount(canTransferSongCount)
        .title(title)
        .build();

    final History savedHistory = historyRepository.save(history);

    return savedHistory.getId();
  }

  public void appendTransferredMusics(
      final Long historyId, final List<DestinationMusic> transferredMusics
  ) {

  }
}
