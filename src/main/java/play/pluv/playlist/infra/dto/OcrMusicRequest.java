package play.pluv.playlist.infra.dto;

import java.util.List;

public record OcrMusicRequest(
    List<Base64EncodedImage> images
) {

  public static OcrMusicRequest from(final List<String> base64EncodedImages) {
    return new OcrMusicRequest(
        base64EncodedImages.stream()
            .map(Base64EncodedImage::new)
            .toList()
    );
  }

  private record Base64EncodedImage(String base64EncodedImage) {

  }
}
