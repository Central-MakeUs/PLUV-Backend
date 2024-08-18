package play.pluv.history.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.pluv.music.domain.MusicId;

@Getter
@RequiredArgsConstructor
public class TransferredMusicInContext {

  private final MusicId musicId;
  private final String title;
  private final String artistNames;
  private final String imageUrl;
  private final String isrcCode;
}
