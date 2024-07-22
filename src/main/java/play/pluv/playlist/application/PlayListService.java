package play.pluv.playlist.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.playlist.domain.PlayList;

@Service
@RequiredArgsConstructor
public class PlayListService {

  private final PlayListConnector playListConnector;

  public List<PlayList> getPlayLists(final String accessToken, final String source) {
    return playListConnector.getPlayList(accessToken);
  }
}
