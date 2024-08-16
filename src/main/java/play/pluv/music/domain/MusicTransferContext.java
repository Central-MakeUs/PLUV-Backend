package play.pluv.music.domain;

import java.util.List;
import lombok.Getter;
import play.pluv.playlist.domain.PlayListMusic;

@Getter
public class MusicTransferContext {

  private final List<PlayListMusic> playListMusics;

  public MusicTransferContext(final List<PlayListMusic> playListMusics) {
    this.playListMusics = playListMusics;
  }
}
