package play.pluv.oauth.spotify.dto;

import static play.pluv.oauth.spotify.dto.ThumbNailResponse.IMAGE_NULL_RESPONSE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import java.util.Optional;
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
      final String thumbNailUrl = Optional.ofNullable(images())
          .map(images -> images.get(0).url())
          .orElse(IMAGE_NULL_RESPONSE);

      return PlayList.builder()
          .playListId(new PlayListId(id, SPOTIFY))
          .thumbNailUrl(thumbNailUrl)
          .songCount(tracks.total())
          .name(name)
          .build();
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
