package play.pluv.oauth.spotify.dto;

public record ThumbNailResponse(
    Integer height,
    Integer width,
    String url
) {

  static final String IMAGE_NULL_RESPONSE = "";
}
