package runner;
/*This class is used to run Cucumber*/

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/feature/Parallel1.feature"}, glue = {"scenarios"})

public class RunFeaturesForParallel1 extends AbstractTestNGCucumberTests {


}
