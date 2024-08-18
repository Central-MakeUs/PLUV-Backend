package play.pluv.history.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import play.pluv.history.domain.MusicTransferContext;
import play.pluv.history.domain.TransferFailMusicInContext;
import play.pluv.history.domain.TransferProgress;
import play.pluv.history.domain.TransferredMusicInContext;

@Component
public class MusicTransferContextManager {

  private final Map<Long, MusicTransferContext> musicTransferContextMap = new HashMap<>();

  public void initContext(
      final Long memberId, final List<TransferFailMusicInContext> transferFailMusic,
      final Integer willTransferMusicCount
  ) {
    final MusicTransferContext context
        = new MusicTransferContext(memberId, transferFailMusic, willTransferMusicCount);
    musicTransferContextMap.put(memberId, context);
  }

  public TransferProgress getCurrentProgress(final Long memberId) {
    return musicTransferContextMap.get(memberId).currentProgress();
  }

  public MusicTransferContext getContext(final Long memberId) {
    return musicTransferContextMap.get(memberId);
  }

  public void addTransferredMusics(
      final Long memberId, final List<TransferredMusicInContext> transferredMusics
  ) {
    final MusicTransferContext context = musicTransferContextMap.get(memberId);
    context.addTransferredMusics(transferredMusics);
  }
}
