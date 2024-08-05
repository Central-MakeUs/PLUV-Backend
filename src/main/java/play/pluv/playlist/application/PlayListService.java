package play.pluv.playlist.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListMusic;

@Service
@RequiredArgsConstructor
public class PlayListService {

  private final PlayListConnectorComposite playListConnectorComposite;

  public List<PlayList> getPlayLists(final String accessToken, final MusicStreaming source) {
    return playListConnectorComposite.getPlayList(source, accessToken);
  }

  public List<PlayListMusic> getPlayListMusics(
      final String playListId, final String accessToken, final MusicStreaming source
  ) {
    return playListConnectorComposite.getMusics(source, accessToken, playListId);
  }
}
