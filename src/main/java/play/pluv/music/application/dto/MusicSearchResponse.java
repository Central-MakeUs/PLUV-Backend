package play.pluv.music.application.dto;

import lombok.Builder;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.SourceMusic;
import play.pluv.playlist.domain.PlayListMusic;

@Builder
public record MusicSearchResponse(
    Boolean isEqual,
    Boolean isFound,
    SourceMusicResponse sourceMusic,
    DestinationMusicResponse destinationMusic
) {

  public static MusicSearchResponse createFound(
      final PlayListMusic sourceMusic, final DestinationMusic destinationMusic
  ) {
    final SourceMusicResponse sourceMusicResponse = SourceMusicResponse.from(sourceMusic);
    final DestinationMusicResponse destinationMusicResponse
        = DestinationMusicResponse.from(destinationMusic);

    return MusicSearchResponse.builder()
        .sourceMusic(sourceMusicResponse)
        .destinationMusic(destinationMusicResponse)
        .isFound(true)
        .isEqual(destinationMusic.isSame(sourceMusic))
        .build();
  }

  public static MusicSearchResponse createNotFound(final PlayListMusic playListMusic) {
    final SourceMusicResponse response = SourceMusicResponse.from(playListMusic);
    return MusicSearchResponse.builder()
        .sourceMusic(response)
        .isEqual(false)
        .isFound(false)
        .build();
  }

  public record SourceMusicResponse(
      String title,
      String artistName,
      String imageUrl
  ) {

    public static SourceMusicResponse from(final PlayListMusic playListMusic) {
      return new SourceMusicResponse(
          playListMusic.getTitle(),
          String.join(",", playListMusic.getArtistNames()),
          playListMusic.getImageUrl()
      );
    }
  }

  public record DestinationMusicResponse(
      String id,
      String title,
      String artistName,
      String imageUrl
  ) {

    public static DestinationMusicResponse from(final DestinationMusic destinationMusic) {
      return new DestinationMusicResponse(
          destinationMusic.getMusicId().id(),
          destinationMusic.getTitle(),
          String.join(",", destinationMusic.getArtistNames()),
          destinationMusic.getImageUrl()
      );
    }
  }
}
