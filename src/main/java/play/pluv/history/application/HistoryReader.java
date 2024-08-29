package play.pluv.history.application;

import static java.util.Comparator.comparing;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.domain.History;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.history.domain.repository.TransferFailMusicRepository;
import play.pluv.history.domain.repository.TransferredMusicRepository;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryReader {

  private final HistoryRepository historyRepository;
  private final TransferredMusicRepository transferredMusicRepository;
  private final TransferFailMusicRepository transferFailMusicRepository;

  public List<History> readByMemberId(final Long memberId) {
    return historyRepository.findByMemberId(memberId).stream()
        .sorted(comparing(History::getCreatedAt).reversed())
        .toList();
  }

  public History readRecentHistoryByMemberId(final Long memberId) {
    return historyRepository.readRecentHistoryByMemberId(memberId);
  }

  public History readById(final Long historyId) {
    return historyRepository.readById(historyId);
  }

  public List<TransferredMusic> readTransferredMusics(final Long historyId) {
    return transferredMusicRepository.findByHistoryId(historyId);
  }

  public List<TransferFailMusic> readTransferFailMusics(final Long historyId) {
    return transferFailMusicRepository.findByHistoryId(historyId);
  }

  public List<TransferredMusic> readFeedMusics(final Long feedId) {
    final History history = historyRepository.readByFeedId(feedId);
    return transferredMusicRepository.findByHistoryId(history.getId());
  }
}
