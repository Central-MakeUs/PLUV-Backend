package play.pluv.oauth.google.dto;


import org.springframework.web.util.HtmlUtils;

public record YoutubeMusicDetail(
    String title, ThumbnailUrls thumbnails
) {

  public String url() {
    return HtmlUtils.htmlUnescape(title);
  }
}
