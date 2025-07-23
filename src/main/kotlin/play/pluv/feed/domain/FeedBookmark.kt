package play.pluv.feed.domain

import jakarta.persistence.*
import play.pluv.base.BaseEntity

@Entity
class FeedBookmark(
    feed: Feed,
    memberId: Long,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @field:ManyToOne
    var feed: Feed = feed
        private set

    var memberId: Long = memberId
        private set
}
