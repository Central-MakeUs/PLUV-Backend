package play.pluv.login.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import play.pluv.oauth.domain.OAuthMemberInfo;

public interface SocialLoginIdRepository extends JpaRepository<SocialLoginId, Long> {

  @Query("""
      select s
      from SocialLoginId s
      join fetch s.member
      where s.oauthMemberInfo = :oauthMemberInfo
      """)
  Optional<SocialLoginId> findByOAuthMemberInfo(final OAuthMemberInfo oauthMemberInfo);

  Boolean existsByOauthMemberInfo(final OAuthMemberInfo oauthMemberInfo);
}
