package play.pluv.playlist.infra.dto;

import java.util.List;
import play.pluv.playlist.domain.PlayListMusic;

public record OcrMusicResponse(
    String artistNames, String songTitle
) {

  public PlayListMusic toPlayListMusic() {
    return new PlayListMusic(songTitle, List.of(artistNames), null, "");
  }
}
