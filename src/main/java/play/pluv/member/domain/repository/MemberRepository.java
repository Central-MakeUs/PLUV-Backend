package play.pluv.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import play.pluv.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
