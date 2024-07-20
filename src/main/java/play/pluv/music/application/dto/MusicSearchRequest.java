package play.pluv.music.application.dto;

import java.util.Arrays;
import java.util.List;
import play.pluv.music.domain.SourceMusic;

public record MusicSearchRequest(
    String destinationAccessToken, List<MusicQuery> musics
) {

  public record MusicQuery(
      String musicName, String artistName, String isrcCode
  ) {

    public SourceMusic toDomain() {
      final List<String> names = Arrays.stream(artistName.split(","))
          .map(String::trim)
          .toList();

      return SourceMusic.builder()
          .name(musicName)
          .artistNames(names)
          .isrcCode(isrcCode)
          .build();
    }
  }
}
