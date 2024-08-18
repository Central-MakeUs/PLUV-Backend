package play.pluv.transfer_context.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.domain.History;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.history.domain.repository.TransferFailMusicRepository;
import play.pluv.history.domain.repository.TransferredMusicRepository;
import play.pluv.music.domain.MusicId;
import play.pluv.transfer_context.domain.MusicTransferContext;
import play.pluv.transfer_context.domain.TransferProgress;
import play.pluv.transfer_context.domain.TransferredMusicInContext;

@Component
public class MusicTransferContextManager {

  private final Map<MusicId, TransferredMusicInContext> destMusicMap = new HashMap<>();
  private final Map<Long, MusicTransferContext> musicTransferContextMap = new HashMap<>();
  private final HistoryRepository historyRepository;
  private final TransferFailMusicRepository transferFailMusicRepository;
  private final TransferredMusicRepository transferredMusicRepository;

  public MusicTransferContextManager(final HistoryRepository historyRepository,
      final TransferFailMusicRepository transferFailMusicRepository,
      final TransferredMusicRepository transferredMusicRepository) {
    this.historyRepository = historyRepository;
    this.transferFailMusicRepository = transferFailMusicRepository;
    this.transferredMusicRepository = transferredMusicRepository;
  }

  public void putDestMusic(final List<TransferredMusicInContext> transferredMusics) {
    transferredMusics.forEach(
        transferredMusic -> destMusicMap.put(transferredMusic.getMusicId(), transferredMusic)
    );
  }

  public void initContext(final MusicTransferContext context) {
    final Long memberId = context.getMemberId();
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
}
