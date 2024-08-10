package play.pluv.history.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.domain.History;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.history.domain.repository.TransferFailMusicRepository;
import play.pluv.member.domain.Member;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.playlist.domain.PlayListMusic;

@Component
@RequiredArgsConstructor
public class HistoryUpdater {

  private final HistoryRepository historyRepository;
  private final TransferFailMusicRepository transferFailMusicRepository;

  @Transactional
  public Long createHistory(
      final String title, final String thumbNailUrl, final Member member,
      final List<PlayListMusic> transferFailMusics, final int totalSongCount
  ) {
    final History history = History.builder()
        .totalSongCount(totalSongCount)
        .thumbNailUrl(thumbNailUrl)
        .memberId(member.getId())
        .transferredSongCount(0)
        .title(title)
        .build();

    final History savedHistory = historyRepository.save(history);
    saveTransferFailMusics(transferFailMusics, savedHistory.getId());

    return savedHistory.getId();
  }

  private void saveTransferFailMusics(
      final List<PlayListMusic> playListMusics, final Long historyId
  ) {
    final List<TransferFailMusic> transferFailMusics = playListMusics.stream()
        .map(plm -> TransferFailMusic.of(historyId, plm))
        .toList();

    transferFailMusicRepository.saveAll(transferFailMusics);
  }

  public void appendTransferredMusics(
      final Long historyId, final List<DestinationMusic> transferredMusics
  ) {

  }
}
