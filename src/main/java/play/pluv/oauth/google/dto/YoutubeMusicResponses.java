package play.pluv.oauth.google.dto;

import java.util.List;
import play.pluv.playlist.domain.PlayListMusic;

public record YoutubeMusicResponses(
    String nextPageToken,
    List<YoutubeMusicResponse> items
) {

  public List<PlayListMusic> toPlayListMusics() {
    return items.stream()
        .map(YoutubeMusicResponse::toPlayListMusic)
        .toList();
  }

  private record YoutubeMusicResponse(
      String id,
      YoutubeMusicDetail snippet
  ) {

    private PlayListMusic toPlayListMusic() {
      return PlayListMusic.builder()
          .imageUrl(snippet.thumbnails().getUrl())
          .title(snippet().title())
          .artistNames(List.of())
          .build();
    }

    private record YoutubeMusicDetail(
        String title, ThumbnailUrls thumbnails
    ) {

    }
  }
}
