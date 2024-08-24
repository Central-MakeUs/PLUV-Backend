package play.pluv.history.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.domain.History;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;

@Service
@RequiredArgsConstructor
public class HistoryService {

  private final HistoryReader historyReader;

  @Transactional(readOnly = true)
  public List<History> findHistories(final Long memberId) {
    return historyReader.readByMemberId(memberId);
  }

  @Transactional(readOnly = true)
  public History findHistory(final Long historyId, final Long memberId) {
    final History history = historyReader.readById(historyId);
    history.validateOwner(memberId);
    return history;
  }

  @Transactional(readOnly = true)
  public List<TransferFailMusic> findTransferFailMusics(final Long historyId, final Long memberId) {
    return historyReader.readTransferFailMusics(historyId);
  }

  @Transactional(readOnly = true)
  public List<TransferredMusic> findTransferredMusics(final Long historyId, final Long memberId) {
    return historyReader.readTransferredMusics(historyId);
  }

  @Transactional(readOnly = true)
  public List<TransferredMusic> findFeedMusics(final Long feedId) {
    return historyReader.readFeedMusics(feedId);
  }
}
