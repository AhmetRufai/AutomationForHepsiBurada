package utility;
/*This class was created to run parallel tests with 2 browser windows.*/

import org.openqa.selenium.WebDriver;
import java.util.HashMap;
import java.util.Map;

public class VariablesFactory {
    public WebDriver driver;

    private static VariablesFactory instance = new VariablesFactory();

    public static VariablesFactory getInstance() {
        return instance;
    }

    private VariablesFactory() {

    }

    private Map<String, VariablesFactory> variableMap = new HashMap<>();

    public enum VariableType {
        PRIMARY, SECONDARY;
    }


    public synchronized VariablesFactory getVariable(VariableType type) {
        VariablesFactory variable = variableMap.get(type.toString());
        if (variable == null) {
            variable = new VariablesFactory();
            variableMap.put(type.toString(), variable);
        }
        return variable;
    }


}
