package play.pluv.music.domain;

import java.util.List;
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
  private final String isrcCode;

  public Boolean isSame(final SourceMusic sourceMusic) {

    return null;
  }
}
