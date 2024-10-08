package play.pluv.oauth.google.dto;

import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.util.List;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;

public record YoutubeSearchMusicResponses(
    List<YoutubeMusicVideo> items
) {

  public DestinationMusics toDestinationMusics() {
    return new DestinationMusics(items.stream()
        .map(YoutubeMusicVideo::toDestinationMusic)
        .limit(5)
        .toList());
  }

  private record YoutubeMusicVideo(
      VideoId id,
      YoutubeMusicDetail snippet
  ) {

    public DestinationMusic toDestinationMusic() {
      return DestinationMusic.builder()
          .musicId(new MusicId(YOUTUBE, getId()))
          .imageUrl(snippet().thumbnails().getUrl())
          .artistNames(List.of())
          .title(snippet().title())
          .isrcCode(null)
          .build();
    }

    private String getId() {
      return id.videoId();
    }

    private record VideoId(
        String videoId
    ) {

    }
  }
}
