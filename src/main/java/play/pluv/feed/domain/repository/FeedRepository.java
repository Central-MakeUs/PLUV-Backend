package play.pluv.feed.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import play.pluv.feed.domain.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {

  Optional<Feed> findByHistoryId(final Long historyId);
}
