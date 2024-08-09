package play.pluv.music.domain;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.pluv.playlist.domain.PlayListMusic;

@Getter
@RequiredArgsConstructor
public class DestinationMusics {

  private final List<DestinationMusic> destinationMusics;

  public Boolean containEqual(final PlayListMusic playListMusic) {
    return destinationMusics.stream()
        .map(destinationMusic -> destinationMusic.isSame(playListMusic))
        //하나라도 동일하면 true 반환
        .reduce(false, (a, b) -> a || b);
  }

  public Boolean isEmpty() {
    return destinationMusics.isEmpty();
  }
}
