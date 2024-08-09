package play.pluv.history.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import play.pluv.history.domain.History;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
