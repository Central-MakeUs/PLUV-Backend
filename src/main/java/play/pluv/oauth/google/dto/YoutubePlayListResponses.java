package play.pluv.oauth.google.dto;

import static play.pluv.music.domain.MusicStreaming.YOUTUBE;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;

public record YoutubePlayListResponses(
    List<YoutubePlayListResponse> items
) {

  public List<PlayList> toPlayLists() {
    return items.stream()
        .map(YoutubePlayListResponse::toPlayList)
        .toList();
  }

  private record YoutubePlayListResponse(
      String id, YoutubePlayListDetail snippet
  ) {

    private PlayList toPlayList() {
      return PlayList.builder()
          .name(snippet.title)
          .thumbNailUrl(snippet.thumbnails.thumbnail.url)
          .playListId(new PlayListId(id, YOUTUBE))
          .build();
    }

    private record YoutubePlayListDetail(
        String title, ThumbnailUrls thumbnails
    ) {

      private record ThumbnailUrls(
          @JsonProperty("default")
          Thumbnail thumbnail
      ) {

        private record Thumbnail(String url) {

        }
      }
    }
  }
}
