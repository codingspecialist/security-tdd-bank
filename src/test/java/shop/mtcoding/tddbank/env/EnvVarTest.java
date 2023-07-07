package shop.mtcoding.tddbank.env;

import org.junit.jupiter.api.Test;

public class EnvVarTest {

    @Test
    public void env_test(){
        String home = System.getenv("JAVA_HOME");
        System.out.println(home);
    }
}
