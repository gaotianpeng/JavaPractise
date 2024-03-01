
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HelloTest {
    @BeforeAll
    public final void beforeAll() {
        System.out.println("before all");
    }
    @AfterAll
    public final void afterAll() {
        System.out.println("after all");
    }

    @Test
    public void sumTest() {

    }
}
