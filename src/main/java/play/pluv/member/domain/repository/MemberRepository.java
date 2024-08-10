package play.pluv.member.domain.repository;

import static play.pluv.member.exception.MemberExceptionType.MEMBER_NOT_FOUND;

import org.springframework.data.jpa.repository.JpaRepository;
import play.pluv.member.domain.Member;
import play.pluv.member.exception.MemberException;

public interface MemberRepository extends JpaRepository<Member, Long> {

  default Member readById(final Long id) {
    return findById(id).orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
  }
}
