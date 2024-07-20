package play.pluv.support;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import play.pluv.music.application.MusicService;
import play.pluv.playlist.application.PlayListService;

@WebMvcTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class ApiTest {

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @MockBean
  protected PlayListService playListService;
  @MockBean
  protected MusicService musicService;

  @BeforeEach
  void setUp(
      final WebApplicationContext applicationContext,
      final RestDocumentationContextProvider provider
  ) {
    mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
        .apply(documentationConfiguration(provider).operationPreprocessors()
            .withRequestDefaults(prettyPrint())
            .withResponseDefaults(prettyPrint()))
        .build();
  }
}
