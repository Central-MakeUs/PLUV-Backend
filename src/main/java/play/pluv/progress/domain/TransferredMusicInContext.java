package play.pluv.progress.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.history.domain.TransferredMusic;

@Getter
@RequiredArgsConstructor
public class TransferredMusicInContext {

  private final HistoryMusicId musicId;
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
        .isrcCode(isrcCode)
        .build();
  }
}
