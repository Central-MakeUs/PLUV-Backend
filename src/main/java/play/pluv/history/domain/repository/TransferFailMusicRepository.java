package play.pluv.history.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import play.pluv.history.domain.TransferFailMusic;

public interface TransferFailMusicRepository extends JpaRepository<TransferFailMusic, Long> {

  List<TransferFailMusic> findByHistoryId(final Long historyId);
}
