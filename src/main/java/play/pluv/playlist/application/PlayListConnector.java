package play.pluv.playlist.application;

import java.util.List;
import play.pluv.playlist.domain.PlayList;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;

public interface PlayListConnector {

  List<PlayList> getPlayList(final String accessToken);

  List<PlayListMusic> getMusics(final String playListId, final String accessToken);

  PlayListId createPlayList(final String accessToken, final String name);

  MusicStreaming supportedType();
}
