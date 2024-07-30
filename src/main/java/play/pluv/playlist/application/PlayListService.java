package play.pluv.playlist.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.oauth.spotify.SpotifyConnector;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListMusic;

@Service
@RequiredArgsConstructor
public class PlayListService {

  private final PlayListConnectorComposite playListConnectorComposite;
  private final SpotifyConnector spotifyConnector;

  public List<PlayList> getPlayLists(final String authKey, final MusicStreaming source) {
    return playListConnectorComposite.getPlayList(source, authKey);
  }

  public List<PlayListMusic> getPlayListMusics(
      final String playListId, final String accessToken, final MusicStreaming source
  ) {
    return spotifyConnector.getMusics(playListId, accessToken);
  }
}
