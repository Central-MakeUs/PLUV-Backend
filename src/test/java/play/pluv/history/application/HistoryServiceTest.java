package play.pluv.history.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static play.pluv.fixture.HistoryFixture.저장된_이전실패한_음악들;
import static play.pluv.fixture.HistoryFixture.저장된_이전한_음악들;
import static play.pluv.fixture.HistoryFixture.저장된_히스토리_1;
import static play.pluv.fixture.HistoryFixture.저장된_히스토리_2;
import static play.pluv.fixture.MemberEntityFixture.멤버_홍혁준;
import static play.pluv.history.exception.HistoryExceptionType.HISTORY_NOT_OWNER;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.history.domain.History;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.history.domain.repository.TransferFailMusicRepository;
import play.pluv.history.domain.repository.TransferredMusicRepository;
import play.pluv.history.exception.HistoryException;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.support.ApplicationTest;

class HistoryServiceTest extends ApplicationTest {

  @Autowired
  private HistoryService historyService;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private HistoryRepository historyRepository;
  @Autowired
  private TransferFailMusicRepository transferFailMusicRepository;
  @Autowired
  private TransferredMusicRepository transferredMusicRepository;

  @Test
  void 히스토리_목록을_조회한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final History history1 = 저장된_히스토리_1(historyRepository, member.getId());
    final History history2 = 저장된_히스토리_2(historyRepository, member.getId());

    final List<History> actual = historyService.findHistories(member.getId());

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(history1, history2);
  }

  @Test
  void 히스토리를_하나_조회한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final History history = 저장된_히스토리_1(historyRepository, member.getId());

    final History actual = historyService.findHistory(history.getId(), member.getId());

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(history);
  }

  @Test
  void 히스토리를_하나_조회하는_대상이_소유자가_아니면_예외를_던진다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final Long invalidMemberId = member.getId() + 1;
    final History history = 저장된_히스토리_1(historyRepository, member.getId());

    assertThatThrownBy(() -> historyService.findHistory(history.getId(), invalidMemberId))
        .isInstanceOf(HistoryException.class)
        .hasMessage(HISTORY_NOT_OWNER.getMessage());
  }

  @Test
  void 이전실패한_음악을_반환한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final History history = 저장된_히스토리_1(historyRepository, member.getId());
    final List<TransferFailMusic> transferFailMusics = 저장된_이전실패한_음악들(
        transferFailMusicRepository,
        history.getId()
    );

    final List<TransferFailMusic> actual = historyService.findTransferFailMusics(
        history.getId(), member.getId()
    );

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .isEqualTo(transferFailMusics);
  }

  @Test
  void 이전한_음악들을_반환한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final History history = 저장된_히스토리_1(historyRepository, member.getId());
    final List<TransferredMusic> transferFailMusics = 저장된_이전한_음악들(
        transferredMusicRepository,
        history.getId()
    );

    final List<TransferredMusic> actual = historyService.findTransferredMusics(
        history.getId(), member.getId()
    );

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .isEqualTo(transferFailMusics);
  }

  @Test
  void 피드의_id로_이전된_음악목록을_조회한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final History history = 저장된_히스토리_1(historyRepository, member.getId());
    final List<TransferredMusic> transferFailMusics = 저장된_이전한_음악들(
        transferredMusicRepository, history.getId()
    );

    final List<TransferredMusic> actual = historyService.findFeedMusics(history.getFeedId());

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .isEqualTo(transferFailMusics);
  }
}