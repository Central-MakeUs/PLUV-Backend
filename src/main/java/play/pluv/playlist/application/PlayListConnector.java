package play.pluv.playlist.application;

import java.util.List;
import play.pluv.music.domain.MusicStreaming;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;

public interface PlayListConnector {

  List<PlayList> getPlayList(final String authKey);

  List<PlayListMusic> getMusics(final String playListId, final String authKey);

  PlayListId createPlayList(final String authKey, final String name);

  MusicStreaming supportedType();
}
