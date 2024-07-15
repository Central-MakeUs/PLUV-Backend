package play.pluv.playlist.application;

import java.util.List;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListProvider;

public interface MusicPlatformConnector {

  //TODO : 최신순 정렬 -> 불가능
  List<PlayList> getPlayList(final String authCode);

  PlayListProvider supportedType();
}
