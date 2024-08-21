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
  public List<History> getHistories(final Long memberId) {
    return historyReader.readByMemberId(memberId);
  }
}
