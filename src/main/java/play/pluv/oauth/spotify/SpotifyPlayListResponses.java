package play.pluv.oauth.spotify;

import java.util.List;
import play.pluv.playlist.domain.PlayList;

public record SpotifyPlayListResponses(
    List<SpotifyPlayListResponse> items
) {

  public record SpotifyPlayListResponse(
      String id,
      List<ThumbNaileResponse> images,
      String name
  ) {

    public record ThumbNaileResponse(
        Integer height,
        Integer width,
        String url
    ) {

    }
  }

  public PlayList toPlayList() {
    return new PlayList();
  }
}
