package play.pluv.transfer_context.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.pluv.history.domain.TransferredMusic;
import play.pluv.music.domain.MusicId;

@Getter
@RequiredArgsConstructor
public class TransferredMusicInContext {

  private final MusicId musicId;
  private final String title;
  private final String artistNames;
  private final String imageUrl;
  private final String isrcCode;

  public TransferredMusic toTransferredMusic(final Long historyId) {
    return TransferredMusic.builder()
        .artistNames(artistNames)
        .historyId(historyId)
        .imageUrl(imageUrl)
        .title(title)
        .musicId(musicId)
        .build();
  }
}
