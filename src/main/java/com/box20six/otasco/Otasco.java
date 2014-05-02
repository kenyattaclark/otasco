package com.box20six.otasco;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * <ul>
 * <li>Allows testing class under test without creating useless setters for
 * dependencies.</li>
 * <li>Makes the test class more readable.</li>
 * </ul>
 *
 * <pre>
 *   public class InvoiceManagerTest {
 *
 *       &#064;Dependency
 *       private InvoiceCalculator invoiceCalculator;
 *
 *       &#064;Dependency
 *       private InvoiceDao invoiceDao;
 *
 *     	 &#064;ClassUnderTest
 *       private InvoiceManager manager;
 *
 *       &#064;Before public void setup() {
 *           invoiceCalculator = new InvoiceCalculator();
 *           invoiceDao = new InvoiceDao();
 *           manager = new InvoiceManager();
 *           Otasco.init(this);
 *       }
 *   }
 *
 * </pre>
 *
 * <b><code>Otasco.init(this)</code></b> method has to called to "wire" the
 * annotated instance of class under test to the annotated dependencies.
 */
public class Otasco {

    private Otasco() {
    }

    /**
     * Wires objects annotated with &#064;ClassUnderTest for given testClass
     * with objects annotated with &#064;Dependency.
     * <p>
     * See examples in javadoc for {@link Otasco} class.
     *
     * @param testClass
     */
    public static void init(final Object testClass) {
        if (testClass == null) {
            throw new OtascoException("testClass cannot be null. For info on how to use @ClassUnderTest and @Dependency see examples in Javadoc for OtascoAnnotations class.");
        }

        final Field classUnderTestField = retrieveClassUnderTest(testClass);

        try {
            final Object classUnderTest = classUnderTestField.get(testClass);

            if (classUnderTest == null) {
                throw new OtascoException("@ClassUnderTest must be initialize prior to calling OtascoAnnotations.init().  For info on how to use @ClassUnderTest and @Dependency see examples in Javadoc for OtascoAnnotations class.");
            }

            dependencyFieldsFromAnnotations2(testClass).forEach(f -> {
                try {
                    final Field df = getDependencyField(classUnderTest, f.getName());
                    makeAccessible(df);
                    makeAccessible(f);
                    df.set(classUnderTest, f.get(testClass));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    throw new OtascoException("Error processing class under test: " + ex.getMessage(), ex);
                }
            });
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Otasco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Field retrieveClassUnderTest(final Object testClass) {
        final Field classUnderTestField = classUnderTestFromAnnotation2(testClass).orElseThrow(() -> {
            return new OtascoException("@ClassUnderTest must be specified.  For info on how to use @ClassUnderTest and @Dependency see examples in Javadoc for OtascoAnnotations class.");
        });
        makeAccessible(classUnderTestField);
        return classUnderTestField;
    }

    /**
     * Attempts to set the named field on a target object with the value passed
     * in.
     *
     * @param name
     * @param value
     * @param target
     */
    public static void setField(final String name, final Object value, final Object target) {
        try {
            FieldUtils.writeField(target, name, value, true);
        } catch (IllegalAccessException e) {
            throw new OtascoException("Error setting field on class: " + e.getMessage(), e);
        }
    }

    private static void makeAccessible(final Field cutField) {
        cutField.setAccessible(true);
    }

    private static Stream<Field> declaredFields2(final Object testClass) {
        return Stream.of(testClass.getClass().getDeclaredFields());
    }

    private static Optional<Field> classUnderTestFromAnnotation2(final Object testClass) {
        return declaredFields2(testClass)
                .filter(field -> field.getAnnotation(ClassUnderTest.class) != null)
                .findFirst();
    }

    private static Stream<Field> dependencyFieldsFromAnnotations2(final Object testClass) {
        return declaredFields2(testClass)
                .filter(field -> field.getAnnotation(Dependency.class) != null);
    }

    private static Field getDependencyField(Object classUnderTest, String dependecyFieldName) { // throws SecurityException, NoSuchFieldException {
        return FieldUtils.getField(classUnderTest.getClass(), dependecyFieldName, true);
    }
}
