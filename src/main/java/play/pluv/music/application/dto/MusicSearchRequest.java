package play.pluv.music.application.dto;

import java.util.Arrays;
import java.util.List;
import play.pluv.playlist.domain.PlayListMusic;

public record MusicSearchRequest(
    String destinationAccessToken, List<MusicQuery> musics
) {

  public record MusicQuery(
      String title, String artistName, String isrcCode, String imageUrl
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
