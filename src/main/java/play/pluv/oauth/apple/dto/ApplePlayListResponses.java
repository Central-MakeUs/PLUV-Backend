package play.pluv.oauth.apple.dto;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import java.util.List;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;

public record ApplePlayListResponses(
    List<ApplePlayListResponse> data
) {

  public List<PlayList> toPlayLists() {
    return data.stream()
        .map(ApplePlayListResponse::toPlayList)
        .toList();
  }

  private record ApplePlayListResponse(
      String id, PlayListAttributes attributes
  ) {

    private PlayList toPlayList() {
      return PlayList.builder()
          .playListId(new PlayListId(id, APPLE))
          .name(attributes.name)
          .thumbNailUrl(attributes.artwork.url)
          .build();
    }

    private record PlayListAttributes(
        Artwork artwork, String name
    ) {

      private record Artwork(
          String url
      ) {

      }
    }
  }
}
