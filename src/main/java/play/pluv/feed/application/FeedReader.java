package play.pluv.feed.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.feed.domain.Feed;
import play.pluv.feed.domain.repository.FeedRepository;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedReader {

  private final FeedRepository feedRepository;

  public List<Feed> findAll() {
    return feedRepository.findAll();
  }
}
