package dk.dtu.grp08.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author Alexander Matzen (s233475)
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = "pretty",
    features = "src/test/resources/features",
    glue = {"dk.dtu.grp08.bdd"}
)
public class CucumberRunner {

}
