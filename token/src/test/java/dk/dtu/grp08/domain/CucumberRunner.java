package dk.dtu.grp08.domain;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author Dilara Eda Celepli (s184262)
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = "pretty",
    features = "src/test/resources/features",
    glue = {"dk.dtu.grp08.domain"}
)
public class CucumberRunner {
}
