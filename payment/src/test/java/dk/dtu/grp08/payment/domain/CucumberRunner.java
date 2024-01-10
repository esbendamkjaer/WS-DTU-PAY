package dk.dtu.grp08.payment.domain;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = "pretty",
    features = "src/test/resources/features",
    glue = {"dk.dtu.grp08.bdd"}
)
public class CucumberRunner {

}
