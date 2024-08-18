package play.pluv.history.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import play.pluv.history.domain.TransferredMusic;

public interface TransferredMusicRepository extends JpaRepository<TransferredMusic, Long> {

  List<TransferredMusic> findByHistoryId(final Long historyId);
}
