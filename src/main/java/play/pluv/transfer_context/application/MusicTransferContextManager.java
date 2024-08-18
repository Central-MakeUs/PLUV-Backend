package play.pluv.transfer_context.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;
import play.pluv.history.application.HistoryUpdater;
import play.pluv.music.domain.MusicId;
import play.pluv.transfer_context.domain.MusicTransferContext;
import play.pluv.transfer_context.domain.TransferFailMusicInContext;
import play.pluv.transfer_context.domain.TransferProgress;
import play.pluv.transfer_context.domain.TransferredMusicInContext;

@Component
public class MusicTransferContextManager {

  private final Map<MusicId, TransferredMusicInContext> destMusicMap = new HashMap<>();
  private final Map<Long, MusicTransferContext> musicTransferContextMap = new HashMap<>();
  private final HistoryUpdater historyUpdater;

  public MusicTransferContextManager(final HistoryUpdater historyUpdater) {
    this.historyUpdater = historyUpdater;
  }

  public void putDestMusic(final List<TransferredMusicInContext> transferredMusics) {
    transferredMusics.forEach(
        transferredMusic -> destMusicMap.put(transferredMusic.getMusicId(), transferredMusic)
    );
  }

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

  public void addTransferredMusics(final Long memberId, final List<MusicId> musicIds) {
    final List<TransferredMusicInContext> transferredMusicInContexts = musicIds.stream()
        .map(destMusicMap::get)
        .filter(Objects::nonNull)
        .toList();
    musicTransferContextMap.get(memberId).addTransferredMusics(transferredMusicInContexts);
  }

  public void saveTransferHistory(final Long memberId) {

  }
}
