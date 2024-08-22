package play.pluv.feed.domain.repository;

import static play.pluv.feed.exception.FeedExceptionType.FEED_NOT_FOUND;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import play.pluv.feed.domain.Feed;
import play.pluv.feed.exception.FeedException;

public interface FeedRepository extends JpaRepository<Feed, Long> {

  Optional<Feed> findByHistoryId(final Long historyId);

  default Feed readById(final Long id) {
    return findById(id).orElseThrow(() -> new FeedException(FEED_NOT_FOUND));
  }
}
