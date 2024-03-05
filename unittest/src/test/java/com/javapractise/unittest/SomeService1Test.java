import com.javapractise.unittest.DependencyService;
import com.javapractise.unittest.SomeService;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SomeService1Test {
    @Test
    public void testCallDependencyServiceMethodWithMocked(@Mocked DependencyService dependencyService) {
        new Expectations() {{
            dependencyService.someMethod(); result = "mocked response";
        }};

        SomeService someService = new SomeService();
        String result = someService.callDependencyServiceMethod();

        assertEquals("mocked response", result);
    }
}
