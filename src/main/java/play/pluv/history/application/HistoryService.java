package play.pluv.history.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.domain.History;

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
}
