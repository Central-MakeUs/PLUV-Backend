package play.pluv.progress.application;

import static play.pluv.progress.exception.ProgressExceptionType.AlREADY_FINISHED_TRANSFER_PROGRESS;
import static play.pluv.progress.exception.ProgressExceptionType.NOT_FINISHED_TRANSFER_PROGRESS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import play.pluv.feed.domain.Feed;
import play.pluv.feed.domain.repository.FeedRepository;
import play.pluv.history.domain.History;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.history.domain.repository.TransferFailMusicRepository;
import play.pluv.history.domain.repository.TransferredMusicRepository;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.progress.domain.MusicTransferContext;
import play.pluv.progress.domain.TransferProgress;
import play.pluv.progress.domain.TransferredMusicInContext;
import play.pluv.progress.exception.ProgressException;

@Component
@RequiredArgsConstructor
public class MusicTransferContextManager {

  private final Map<HistoryMusicId, TransferredMusicInContext> destMusicMap = new HashMap<>();
  private final Map<Long, MusicTransferContext> musicTransferContextMap = new HashMap<>();
  private final HistoryRepository historyRepository;
  private final TransferFailMusicRepository transferFailMusicRepository;
  private final TransferredMusicRepository transferredMusicRepository;
  private final FeedRepository feedRepository;
  private final MemberRepository memberRepository;

  public void putDestMusic(final List<TransferredMusicInContext> transferredMusics) {
    transferredMusics.forEach(
        transferredMusic -> destMusicMap.put(transferredMusic.getMusicId(), transferredMusic)
    );
  }

  public void initContext(final MusicTransferContext context) {
    final Long memberId = context.getMemberId();
    if (musicTransferContextMap.containsKey(memberId)) {
      throw new ProgressException(NOT_FINISHED_TRANSFER_PROGRESS);
    }
    musicTransferContextMap.put(memberId, context);
  }

  public TransferProgress getCurrentProgress(final Long memberId) {
    return Optional.ofNullable(musicTransferContextMap.get(memberId))
        .map(MusicTransferContext::currentProgress)
        .orElseThrow(() -> new ProgressException(AlREADY_FINISHED_TRANSFER_PROGRESS));
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
    final Member member = memberRepository.readById(memberId);
    final MusicTransferContext context = musicTransferContextMap.get(memberId);

    final Feed feed = context.createFeed(member.getNickName().getNickName());
    feedRepository.save(feed);

    final History history = historyRepository.save(context.toHistory(feed.getId()));

    final List<TransferFailMusic> transferFailMusics = context.extractTransferFailMusics(
        history.getId()
    );
    transferFailMusicRepository.saveAll(transferFailMusics);

    final List<TransferredMusic> transferredMusics = context.extractTransferredMusics(
        history.getId()
    );
    transferredMusicRepository.saveAll(transferredMusics);

    musicTransferContextMap.remove(memberId);
    return history.getId();
  }
}
