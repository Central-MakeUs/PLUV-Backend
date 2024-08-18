package play.pluv.history.domain.repository;

import static play.pluv.history.exception.HistoryExceptionType.HISTORY_NOT_FOUND;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import play.pluv.history.domain.History;
import play.pluv.history.exception.HistoryException;

public interface HistoryRepository extends JpaRepository<History, Long> {

  Optional<History> findByMemberId(final Long memberId);

  default History readByMemberId(final Long memberId) {
    return findByMemberId(memberId).orElseThrow(() -> new HistoryException(HISTORY_NOT_FOUND));
  }
}
