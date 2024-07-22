package play.pluv.playlist.application;

import java.util.List;
import play.pluv.playlist.domain.PlayList;
import play.pluv.music.domain.MusicStreaming;

public interface PlayListConnector {

  List<PlayList> getPlayList(final String accessToken);

  MusicStreaming supportedType();
}
