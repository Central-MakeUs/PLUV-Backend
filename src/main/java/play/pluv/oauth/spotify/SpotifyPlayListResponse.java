package play.pluv.oauth.spotify;

import play.pluv.playlist.domain.PlayList;

public record SpotifyPlayListResponse(

) {

  public PlayList toPlayList() {
    return new PlayList();
  }
}
