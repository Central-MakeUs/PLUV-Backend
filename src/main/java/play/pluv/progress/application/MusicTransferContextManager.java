package play.pluv.progress.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.history.domain.History;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.history.domain.repository.TransferFailMusicRepository;
import play.pluv.history.domain.repository.TransferredMusicRepository;
import play.pluv.progress.domain.MusicTransferContext;
import play.pluv.progress.domain.TransferProgress;
import play.pluv.progress.domain.TransferredMusicInContext;

@Component
@RequiredArgsConstructor
public class MusicTransferContextManager {

  private final Map<HistoryMusicId, TransferredMusicInContext> destMusicMap = new HashMap<>();
  private final Map<Long, MusicTransferContext> musicTransferContextMap = new HashMap<>();
  private final HistoryRepository historyRepository;
  private final TransferFailMusicRepository transferFailMusicRepository;
  private final TransferredMusicRepository transferredMusicRepository;

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

  public void addTransferredMusics(final Long memberId, final List<HistoryMusicId> musicIds) {
    final List<TransferredMusicInContext> transferredMusicInContexts = musicIds.stream()
        .map(destMusicMap::get)
        .filter(Objects::nonNull)
        .toList();
    musicTransferContextMap.get(memberId).addTransferredMusics(transferredMusicInContexts);
  }

  @Transactional
  public Long saveTransferHistory(final Long memberId) {
    final MusicTransferContext context = musicTransferContextMap.get(memberId);
    final History history = historyRepository.save(context.toHistory());

    final List<TransferFailMusic> transferFailMusics = context.extractTransferFailMusics(
        history.getId());
    transferFailMusicRepository.saveAll(transferFailMusics);

    final List<TransferredMusic> transferredMusics = context.extractTransferredMusics(
        history.getId());
    transferredMusicRepository.saveAll(transferredMusics);

    return history.getId();
  }
}
