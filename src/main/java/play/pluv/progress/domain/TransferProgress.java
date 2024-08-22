package play.pluv.progress.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransferProgress {

  private final Integer willTransferMusicCount;
  private final Integer transferredMusicCount;
}
