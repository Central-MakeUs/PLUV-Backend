package play.pluv.feed.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.feed.domain.Feed;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final FeedReader feedReader;

  public List<Feed> findAll() {
    return feedReader.findAll();
  }
}
