package play.pluv.playlist.application;

import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListProvider;

public interface MusicPlatformConnector {

  PlayList getPlayList(final String authCode);

  PlayListProvider supportedType();
}
