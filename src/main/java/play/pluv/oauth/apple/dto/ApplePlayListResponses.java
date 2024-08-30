package play.pluv.oauth.apple.dto;

import static java.util.Comparator.comparing;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;

public record ApplePlayListResponses(
    List<ApplePlayListResponse> data
) {

  public PlayListId recentPlayListIds() {
    return data.stream()
        .sorted(comparing(ApplePlayListResponse::recentUpdatedDate).reversed())
        .limit(1)
        .map(ApplePlayListResponse::toPlayList)
        .toList().get(0).getPlayListId();
  }

  public List<PlayList> toPlayLists() {
    return data.stream()
        .map(ApplePlayListResponse::toPlayList)
        .toList();
  }

  private record ApplePlayListResponse(
      String id, PlayListAttributes attributes
  ) {

    private LocalDateTime recentUpdatedDate() {
      return attributes.lastModifiedDate();
    }

    private PlayList toPlayList() {
      return PlayList.builder()
          .playListId(new PlayListId(id, APPLE))
          .name(attributes.name)
          .thumbNailUrl(attributes.artworkUrl())
          .build();
    }

    private record PlayListAttributes(
        Artwork artwork, String name, LocalDateTime lastModifiedDate
    ) {

      public String artworkUrl() {
        return Optional.ofNullable(artwork)
            .map(Artwork::url)
            .orElse("");
      }
    }

  }
}
