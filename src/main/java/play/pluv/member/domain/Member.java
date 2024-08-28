package play.pluv.member.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.lang.Boolean.FALSE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString
public class Member {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Embedded
  private NickName nickName;

  private Boolean deleted = FALSE;

  public Member(final NickName nickName) {
    this.nickName = nickName;
  }

  public void updateNickName(final NickName nickName) {
    this.nickName = nickName;
  }
}
