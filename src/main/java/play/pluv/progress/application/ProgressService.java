package play.pluv.progress.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import play.pluv.progress.domain.TransferProgress;

@RequiredArgsConstructor
@Service
public class ProgressService {

  private final MusicTransferContextManager musicTransferContextManager;

  public TransferProgress getTransferProgress(final Long memberId) {
    return musicTransferContextManager.getCurrentProgress(memberId);
  }
}
