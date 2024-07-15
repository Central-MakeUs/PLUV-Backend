package play.pluv.playlist.application;

import java.util.List;
import org.springframework.stereotype.Service;
import play.pluv.playlist.domain.PlayList;

@Service
public class PlayListService {

  private final MusicPlatformConnector musicPlatformConnector;

  public PlayListService(final MusicPlatformConnector musicPlatformConnector) {
    this.musicPlatformConnector = musicPlatformConnector;
  }

  public List<PlayList> getPlayLists(final String authCode, final String source) {
    return List.of();
  }
}
