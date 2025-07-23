package play.pluv.feed.controller

import org.springframework.web.bind.annotation.*
import play.pluv.base.BaseResponse
import play.pluv.base.BaseResponse.Companion.ok
import play.pluv.feed.application.FeedService
import play.pluv.feed.application.dto.FeedDetailResponse
import play.pluv.feed.application.dto.FeedListResponse
import play.pluv.security.JwtMemberId

@RestController
@RequestMapping("/feed")
class FeedController(
    private val feedService: FeedService
) {

    @get:GetMapping
    val feeds: BaseResponse<List<FeedListResponse>>
        get() {
            val feeds = feedService.findAll()
            val responses = FeedListResponse.createList(feeds)
            return ok(responses)
        }

    @GetMapping("/{id}")
    fun getFeed(
        @PathVariable id: Long, jwtMemberIdq: JwtMemberId
    ): BaseResponse<FeedDetailResponse> {
        val feedResponse = feedService.findFeed(id = id, memberId = jwtMemberIdq.memberId)
        return ok(feedResponse)
    }

    @PostMapping("/{id}/save")
    fun bookmarkFeed(
        jwtMemberId: JwtMemberId, @PathVariable id: Long
    ): BaseResponse<String> {
        feedService.bookmarkFeed(memberId = jwtMemberId.memberId, feedId = id)
        return ok("")
    }

    @DeleteMapping("/{id}/save")
    fun cancelBookmarkFeed(
        jwtMemberId: JwtMemberId, @PathVariable id: Long
    ): BaseResponse<String> {
        feedService.cancelBookmark(jwtMemberId.memberId, id)
        return ok("")
    }

    @GetMapping("/save")
    fun getBookmarkFeeds(jwtMemberId: JwtMemberId): BaseResponse<List<FeedListResponse>> {
        val feeds = feedService.findBookmarkedFeeds(memberId = jwtMemberId.memberId)
        val responses = FeedListResponse.createList(feeds)
        return ok(responses)
    }
}
