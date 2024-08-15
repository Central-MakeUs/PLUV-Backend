package play.pluv.oauth.apple.dto;

import java.util.List;
import play.pluv.playlist.domain.PlayListMusic;

public record ApplePlayListMusicResponses(
    List<ApplePlayListMusicResponse> data
) {

  public List<PlayListMusic> toPlayListMusics() {
    return data.stream()
        .map(ApplePlayListMusicResponse::toPlayListMusic)
        .toList();
  }

  private record ApplePlayListMusicResponse(
      String id, MusicAttributes attributes
  ) {

    private PlayListMusic toPlayListMusic() {
      return PlayListMusic.builder()
          .title(attributes.name)
          .artistNames(List.of(attributes.artistName))
          .isrcCode(null)
          .imageUrl(attributes.artwork.url())
          .build();
    }

    private record MusicAttributes(
        Artwork artwork, String name, String artistName
    ) {

    }
  }
}
