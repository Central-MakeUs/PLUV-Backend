package play.pluv.music.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import play.pluv.playlist.domain.PlayListMusic;

public record MusicSearchRequest(
    @NotBlank String destinationAccessToken,
    @NotNull @Valid List<MusicQuery> musics
) {

  public record MusicQuery(
      @NotBlank String title, @NotNull String artistName, String isrcCode, @NotNull String imageUrl
  ) {

    public PlayListMusic toDomain() {
      final List<String> names = Arrays.stream(artistName.split(","))
          .map(String::trim)
          .toList();

      return PlayListMusic.builder()
          .title(title)
          .artistNames(names)
          .imageUrl(imageUrl)
          .isrcCode(isrcCode)
          .build();
    }
  }
}
