package dk.dtu.grp08.account.domain;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = "dk.dtu.grp08.account.domain.steps"
)
public class CucumberRunner {
}
