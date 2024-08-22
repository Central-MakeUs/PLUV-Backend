package play.pluv.feed.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.feed.application.FeedService;
import play.pluv.feed.application.dto.FeedListResponse;
import play.pluv.security.JwtMemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {

  private final FeedService feedService;

  @GetMapping
  public BaseResponse<List<FeedListResponse>> getFeeds() {
    final var feeds = feedService.findAll();
    final List<FeedListResponse> responses = FeedListResponse.createList(feeds);
    return BaseResponse.ok(responses);
  }

  @PostMapping("/{id}/save")
  public BaseResponse<String> bookmarkFeed(
      final JwtMemberId jwtMemberId, @PathVariable final Long id
  ) {
    feedService.bookmarkFeed(jwtMemberId.memberId(), id);
    return BaseResponse.ok("");
  }
}
