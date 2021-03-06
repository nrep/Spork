package io.github.sporklibrary.test.components;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.exceptions.BindException;

import org.junit.Test;

public class ComponentFactoryTests {

    public class TestParent {

        @BindComponent
        private TestComponent testComponent;

        public TestParent() {
            Spork.bind(this);
        }
    }

    public class TestComponent {

        public TestComponent() {
        }

        public TestComponent(Object nothing) {
        }
    }

    @Test(expected = BindException.class)
    public void test() {
        new TestParent();
    }
}
