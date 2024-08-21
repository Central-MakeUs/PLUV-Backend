package play.pluv.history.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.domain.History;
import play.pluv.history.domain.repository.HistoryRepository;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryReader {

  private final HistoryRepository historyRepository;

  public List<History> readByMemberId(final Long memberId) {
    return historyRepository.findByMemberId(memberId);
  }

  public History readById(final Long historyId) {
    return historyRepository.readById(historyId);
  }
}
