package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.runner.RunWith;

@QuarkusTest
@io.cucumber.junit.platform.engine.Cucumber
@RunWith(Cucumber.class)
@CucumberOptions(plugin="summary"
        , publish= false
        , features = "features"  // directory of the feature files
        , snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class CucumberTest {
}