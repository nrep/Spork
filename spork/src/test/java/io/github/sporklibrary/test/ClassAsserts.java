package io.github.sporklibrary.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class ClassAsserts {

    private ClassAsserts() {
    }

    /**
     * Based on http://stackoverflow.com/a/10872497/3848666
     *
     * @param classObject the class
     */
    public static void assertUtilityClassWellDefined(final Class<?> classObject)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        assertTrue("class must be final", Modifier.isFinal(classObject.getModifiers()));
        assertEquals("There must be only one constructor", 1,
                classObject.getDeclaredConstructors().length);

        final Constructor<?> constructor = classObject.getDeclaredConstructor();

        if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
            fail("constructor is not private");
        }

        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);

        for (final Method method : classObject.getMethods()) {
            if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(classObject)) {
                fail("there exists a non-static method:" + method);
            }
        }
    }

    /**
     * This assert is to test inner classes of Annotations that act as a default value for a field
     * such as: <code>Class<?> value() default Default.class</code> as can be found in {@link
     * io.github.sporklibrary.annotations.BindComponent}
     *
     * @param classObject the class
     */
    public static void assertAnnotationDefaultClassWellDefined(final Class<?> classObject)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        assertTrue("class must be final", Modifier.isFinal(classObject.getModifiers()));
        assertEquals("There must be only one constructor", 1,
                classObject.getDeclaredConstructors().length);

        final Constructor<?> constructor = classObject.getDeclaredConstructor();

        if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
            fail("constructor is not private");
        }

        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
    }
}
