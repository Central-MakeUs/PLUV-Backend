package play.pluv.music.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class DestinationMusic {

  private final MusicId musicId;
  private final List<String> artistNames;
  private final String name;
  private final String imageUrl;
  //nullable한 값
  private final String isrcCode;

  public Boolean isSame(final SourceMusic sourceMusic) {
    if (isrcCode != null) {
      return sourceMusic.getIsrcCode()
          .map(isrc -> isrc.equals(isrcCode))
          .orElseGet(() -> compareName(sourceMusic));
    }
    return compareName(sourceMusic);
  }

  private Boolean compareName(final SourceMusic sourceMusic) {
    final Set<String> set1 = new HashSet<>(artistNames);
    final Set<String> set2 = new HashSet<>(sourceMusic.getArtistNames());

    return Objects.equals(sourceMusic.getName(), name) && set1.equals(set2);
  }
}
