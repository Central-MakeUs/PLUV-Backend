package play.pluv.feed.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING
import play.pluv.feed.domain.Feed
import java.time.LocalDate

data class FeedListResponse(
    val id: Long,
    val title: String,
    val thumbNailUrl: String,
    val artistNames: String,
    val creatorName: String,
    @field:JsonFormat(shape = STRING, pattern = "yyyy.MM.dd")
    @param:JsonFormat(shape = STRING, pattern = "yyyy.MM.dd")
    val transferredAt: LocalDate,
    val totalSongCount: Int
) {
    companion object {
        private fun from(feed: Feed): FeedListResponse {
            return FeedListResponse(
                id = feed.getId(),
                title = feed.title,
                thumbNailUrl = feed.thumbNailUrl,
                artistNames = feed.artistNames,
                creatorName = feed.creatorName,
                transferredAt = feed.getCreatedAt().toLocalDate(),
                totalSongCount = feed.songCount
            )
        }

        fun createList(feeds: List<Feed>): List<FeedListResponse> {
            return feeds.map { from(it) }
        }
    }
}
