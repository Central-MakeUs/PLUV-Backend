package play.pluv.feed.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING
import play.pluv.feed.domain.Feed
import java.time.LocalDate

data class FeedDetailResponse(
    val id: Long,
    val songCount: Int,
    val title: String,
    val imageUrl: String,
    val creatorName: String,
    val isBookMarked: Boolean,
    @field:JsonFormat(shape = STRING, pattern = "yyyy.MM.dd")
    @param:JsonFormat(shape = STRING, pattern = "yyyy.MM.dd")
    val createdAt: LocalDate
) {
    companion object {
        @JvmStatic
        fun from(feed: Feed, isBookMarked: Boolean): FeedDetailResponse {
            return FeedDetailResponse(
                id = feed.getId(),
                songCount = feed.songCount,
                title = feed.title,
                imageUrl = feed.thumbNailUrl,
                creatorName = feed.creatorName,
                isBookMarked = isBookMarked,
                createdAt = feed.getCreatedAt().toLocalDate()
            )
        }
    }
}
