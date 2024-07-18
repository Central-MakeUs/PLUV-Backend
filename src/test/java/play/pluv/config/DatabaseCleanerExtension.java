package play.pluv.config;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseCleanerExtension implements BeforeEachCallback {

  @Override
  public void beforeEach(final ExtensionContext context) {
    final DatabaseCleaner databaseCleaner = getDataCleaner(context);
    databaseCleaner.clear();
  }

  private DatabaseCleaner getDataCleaner(final ExtensionContext extensionContext) {
    return SpringExtension.getApplicationContext(extensionContext)
        .getBean(DatabaseCleaner.class);
  }
}
