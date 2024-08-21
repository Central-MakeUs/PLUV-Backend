package play.pluv.history.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import play.pluv.history.domain.History;
import play.pluv.history.domain.repository.HistoryRepository;

@Component
@RequiredArgsConstructor
public class HistoryReader {

  private final HistoryRepository historyRepository;

  public List<History> readByMemberId(final Long memberId) {
    return historyRepository.findByMemberId(memberId);
  }
}
