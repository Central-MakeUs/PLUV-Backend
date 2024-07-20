package play.pluv.music.application.dto;

import lombok.Builder;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.SourceMusic;

@Builder
public record MusicSearchResponse(
    Boolean isEqual,
    Boolean isFound,
    SourceMusicResponse sourceMusic,
    DestinationMusicResponse destinationMusic
) {

  public static MusicSearchResponse createFound(
      final SourceMusic sourceMusic, final DestinationMusic destinationMusic
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

  public static MusicSearchResponse createNotFound(final SourceMusic sourceMusic) {
    final SourceMusicResponse response = SourceMusicResponse.from(sourceMusic);
    return MusicSearchResponse.builder()
        .sourceMusic(response)
        .isEqual(false)
        .isFound(false)
        .build();
  }

  public record SourceMusicResponse(
      String name,
      String artistName
  ) {

    public static SourceMusicResponse from(final SourceMusic sourceMusic) {
      return new SourceMusicResponse(
          sourceMusic.getName(),
          String.join(",", sourceMusic.getArtistNames())
      );
    }
  }

  public record DestinationMusicResponse(
      String id,
      String name,
      String artistName,
      String imageUrl
  ) {

    public static DestinationMusicResponse from(final DestinationMusic destinationMusic) {
      return new DestinationMusicResponse(
          destinationMusic.getMusicId().id(),
          destinationMusic.getName(),
          String.join(",", destinationMusic.getArtistNames()),
          destinationMusic.getImageUrl()
      );
    }
  }
}
