package play.pluv.music.application.dto;

import java.util.List;
import lombok.Builder;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.playlist.domain.PlayListMusic;

@Builder
public record MusicSearchResponse(
    Boolean isEqual,
    Boolean isFound,
    SourceMusicResponse sourceMusic,
    List<DestinationMusicResponse> destinationMusics
) {

  public static MusicSearchResponse createFound(
      final PlayListMusic sourceMusic, final DestinationMusics destinationMusics
  ) {
    final SourceMusicResponse sourceMusicResponse = SourceMusicResponse.from(sourceMusic);
    final List<DestinationMusicResponse> destinationMusicResponses
        = DestinationMusicResponse.extractEqualOrConvert(destinationMusics.getDestinationMusics(),
        sourceMusic);

    return MusicSearchResponse.builder()
        .sourceMusic(sourceMusicResponse)
        .destinationMusics(destinationMusicResponses)
        .isFound(true)
        .isEqual(destinationMusics.containEqual(sourceMusic))
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

    public static List<DestinationMusicResponse> from(
        final List<DestinationMusic> destinationMusics
    ) {
      return destinationMusics.stream()
          .map(DestinationMusicResponse::from)
          .toList();
    }

    public static List<DestinationMusicResponse> extractEqualOrConvert(
        final List<DestinationMusic> destinationMusics, final PlayListMusic playListMusic
    ) {
      return destinationMusics.stream()
          .filter(destinationMusic -> destinationMusic.isSame(playListMusic))
          .findAny()
          .map(destinationMusic -> List.of(DestinationMusicResponse.from(destinationMusic)))
          .orElseGet(() -> DestinationMusicResponse.from(destinationMusics));
    }
  }
}
