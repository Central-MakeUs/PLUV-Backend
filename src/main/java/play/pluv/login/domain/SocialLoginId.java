package play.pluv.login.domain;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import play.pluv.member.domain.Member;
import play.pluv.oauth.domain.OAuthMemberInfo;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class SocialLoginId {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @ManyToOne(cascade = PERSIST)
  @Getter
  @NotNull
  private Member member;
  @NotNull
  @Embedded
  private OAuthMemberInfo oauthMemberInfo;

  @Builder
  public SocialLoginId(final Member member, final OAuthMemberInfo oauthMemberInfo) {
    this.member = member;
    this.oauthMemberInfo = oauthMemberInfo;
  }
}
