package play.pluv.oauth.apple.dto;

public record Artwork(
    String url
) {

  public String url() {
    return url.replace("{w}", "640")
        .replace("{h}", "640");
  }
}
