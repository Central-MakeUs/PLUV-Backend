package play.pluv.oauth.google.dto;

import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

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
      return new PlayList(
          new PlayListId(id, YOUTUBE),
          snippet.title,
          snippet.thumbnails.getUrl()
      );
    }

    private record YoutubePlayListDetail(
        String title, ThumbnailUrls thumbnails
    ) {

    }
  }
}
