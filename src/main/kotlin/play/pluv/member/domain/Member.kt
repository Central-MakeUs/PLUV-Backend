package play.pluv.member.domain

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class Member(
    @field:Embedded nickName: NickName
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null
        private set

    var nickName: NickName = nickName
        private set

    val identifier
        get() = id!!

    fun updateNickName(nickName: NickName) {
        this.nickName = nickName
    }
}