package play.pluv.history.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.HistoryFixture.히스토리_1;
import static play.pluv.fixture.HistoryFixture.히스토리_2;
import static play.pluv.fixture.MemberEntityFixture.멤버_홍혁준;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.history.domain.History;
import play.pluv.history.domain.repository.HistoryRepository;
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

  @Test
  void 히스토리_목록을_조회한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final History history1 = 히스토리_1(historyRepository, member.getId());
    final History history2 = 히스토리_2(historyRepository, member.getId());

    final List<History> actual = historyService.findHistories(member.getId());

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(history1, history2);
  }

  @Test
  void 히스토리를_하나_조회한다() {
    final Member member = 멤버_홍혁준(memberRepository);
    final History history = 히스토리_1(historyRepository, member.getId());

    final History actual = historyService.findHistory(history.getId());

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(history);
  }
}