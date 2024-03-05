package com.javapractise.unittest;

import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.Test;
import mockit.Mocked;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SomeServiceTest {
    @Test
    public void testSomeServiceMethod() {
        new MockUp<DependencyService>() {
            @Mock
            public String someMethod() {
                return "mocked response";
            }
        };

        SomeService someService = new SomeService();
        String result = someService.callDependencyServiceMethod();
        assertEquals("mocked response", result);
    }
}
