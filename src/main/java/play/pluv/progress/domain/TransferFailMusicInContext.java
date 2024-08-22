package play.pluv.progress.domain;

import lombok.RequiredArgsConstructor;
import play.pluv.history.domain.TransferFailMusic;

@RequiredArgsConstructor
public class TransferFailMusicInContext {

  private final String title;
  private final String imageUrl;
  private final String artistNames;

  public TransferFailMusic toTransferFailMusic(final Long historyId) {
    return TransferFailMusic.builder()
        .artistNames(artistNames)
        .title(title)
        .imageUrl(imageUrl)
        .historyId(historyId)
        .build();
  }
}
