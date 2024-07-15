package play.pluv.playlist.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.playlist.domain.PlayList;

@Service
@RequiredArgsConstructor
public class PlayListService {

  private final MusicPlatformConnector musicPlatformConnector;

  public List<PlayList> getPlayLists(final String authCode, final String source) {
    return musicPlatformConnector.getPlayList(authCode);
  }
}
