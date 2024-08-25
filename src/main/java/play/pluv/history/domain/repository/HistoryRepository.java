package play.pluv.history.domain.repository;

import static play.pluv.history.exception.HistoryExceptionType.HISTORY_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import play.pluv.history.domain.History;
import play.pluv.history.exception.HistoryException;

public interface HistoryRepository extends JpaRepository<History, Long> {

  List<History> findByMemberId(final Long memberId);

  Optional<History> findByFeedId(final Long feedId);

  @Query("""
      select h 
      from History h
      where h.memberId=:memberId
      order by h.createdAt desc
      limit 1
      """)
  Optional<History> findRecentHistoryByMemberId(final Long memberId);

  default History readById(final Long id) {
    return findById(id).orElseThrow(() -> new HistoryException(HISTORY_NOT_FOUND));
  }

  default History readByFeedId(final Long feedId) {
    return findByFeedId(feedId).orElseThrow(() -> new HistoryException(HISTORY_NOT_FOUND));
  }

  default History readRecentHistoryByMemberId(final Long memberId) {
    return findRecentHistoryByMemberId(memberId)
        .orElseThrow(() -> new HistoryException(HISTORY_NOT_FOUND));
  }
}

