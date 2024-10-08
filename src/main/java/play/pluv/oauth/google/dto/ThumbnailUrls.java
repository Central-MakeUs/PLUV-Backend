package play.pluv.oauth.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

record ThumbnailUrls(
    @JsonProperty("high")
    Thumbnail thumbnail
) {

  public String getUrl() {
    return thumbnail.url;
  }

  private record Thumbnail(String url) {

  }
}
