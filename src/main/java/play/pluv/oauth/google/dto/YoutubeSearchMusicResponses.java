package play.pluv.oauth.google.dto;

import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.util.List;
import java.util.Optional;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;

public record YoutubeSearchMusicResponses(
    List<YoutubeMusicVideo> items
) {

  public Optional<DestinationMusic> toDestinationMusic() {
    if (items.isEmpty()) {
      return Optional.empty();
    }
    final YoutubeMusicVideo youtubeMusicVideo = items.get(0);

    return Optional.of(DestinationMusic.builder()
        .musicId(new MusicId(YOUTUBE, youtubeMusicVideo.getId()))
        .imageUrl(youtubeMusicVideo.snippet().thumbnails().getUrl())
        .artistNames(List.of())
        .title(youtubeMusicVideo.snippet().title())
        .isrcCode(null)
        .build()
    );
  }

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
          .musicId(new MusicId(YOUTUBE, id.videoId()))
          .imageUrl(snippet().thumbnails().getUrl())
          .artistNames(List.of())
          .title(snippet().title())
          .isrcCode(null)
          .build();
    }

    private String getId() {
      return id.videoId();
    }

    private record YoutubeMusicDetail(
        String title,
        ThumbnailUrls thumbnails
    ) {

    }

    private record VideoId(
        String videoId
    ) {

    }
  }
}
