package play.pluv.oauth.spotify;

import static play.pluv.playlist.domain.PlayListProvider.SPOTIFY;

import java.util.List;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;

public record SpotifyPlayListResponses(
    List<SpotifyPlayListResponse> items
) {

  public record SpotifyPlayListResponse(
      String id,
      List<ThumbNailResponse> images,
      TrackOverviewResponse tracks,
      String name
  ) {

    public PlayList toPlayList() {
      return PlayList.builder()
          .playListId(new PlayListId(id, SPOTIFY))
          .thumbNailUrl(images.get(0).url())
          .songCount(tracks.total())
          .name(name)
          .build();
    }

    public record ThumbNailResponse(
        Integer height,
        Integer width,
        String url
    ) {

    }

    public record TrackOverviewResponse(
        String href,
        Integer total
    ) {

    }
  }

  public List<PlayList> toPlayLists() {
    return items.stream()
        .map(SpotifyPlayListResponse::toPlayList)
        .toList();
  }
}
