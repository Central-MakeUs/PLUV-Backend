package play.pluv.support;

import static org.mockito.Mockito.when;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import play.pluv.base.LocalDateTimeProvider;
import play.pluv.feed.application.FeedService;
import play.pluv.history.application.HistoryService;
import play.pluv.login.application.LoginService;
import play.pluv.member.application.MemberService;
import play.pluv.music.application.MusicService;
import play.pluv.oauth.application.OAuthService;
import play.pluv.playlist.application.PlayListService;
import play.pluv.progress.application.ProgressService;
import play.pluv.security.JwtProvider;
import play.pluv.support.ApiTest.RestDocsResultConfig;

@WebMvcTest
@Import({JwtProvider.class, RestDocsResultConfig.class})
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ApiTest {

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @MockBean
  protected PlayListService playListService;
  @MockBean
  protected MusicService musicService;
  @MockBean
  protected LoginService loginService;
  @MockBean
  protected OAuthService oAuthService;
  @MockBean
  protected MemberService memberService;
  @MockBean
  protected ProgressService progressService;
  @MockBean
  protected HistoryService historyService;
  @MockBean
  protected FeedService feedService;
  @MockBean
  protected JwtProvider jwtProvider;

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

  protected void setAccessToken(final String accessToken, final Long id) {
    when(jwtProvider.parseMemberId(accessToken)).thenReturn(id);
  }

  protected void setCreateToken(final String accessToken, final Long id) {
    when(jwtProvider.createAccessTokenWith(id)).thenReturn(accessToken);
  }

  @Configuration
  static class RestDocsResultConfig {

    @Bean
    public LocalDateTimeProvider localDateTimeProvider() {
      return new LocalDateTimeProvider();
    }
  }
}
