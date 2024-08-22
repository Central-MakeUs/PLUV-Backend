package play.pluv.feed.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import play.pluv.feed.domain.FeedBookmark;

public interface FeedBookmarkRepository extends JpaRepository<FeedBookmark, Long> {

  @Query("""
      select fb
      from FeedBookmark fb
      join fetch fb.feed
      where fb.memberId = :memberId
      """)
  List<FeedBookmark> findByMemberIdWithJoin(final Long memberId);
}
