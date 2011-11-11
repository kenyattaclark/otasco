package com.brightdome.testutil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OtascoAnnotations {

	public static void init(final Object testClass) {
		if (testClass == null) {
			throw new OtascoException("testClass cannot be null. For info on how to use @ClassUnderTest and @Dependency see examples in Javadoc for OtascoAnnotations class.");
	    }

		final List<Field> dependencyFields = new ArrayList<Field>();
		Field classUnderTestField = null;

		for (Field field : testClass.getClass().getDeclaredFields()) {
			final Dependency dependency = field.getAnnotation(Dependency.class);
			final ClassUnderTest cutAnnotation = field.getAnnotation(ClassUnderTest.class);
			if (dependency != null) {
				dependencyFields.add(field);
			} else if (cutAnnotation != null) {
				classUnderTestField = field;
			}
		}

		if (classUnderTestField == null) {
			throw new OtascoException("@ClassUnderTest must be specified.  For info on how to use @ClassUnderTest and @Dependency see examples in Javadoc for OtascoAnnotations class.");
		}

		makeAccessible(classUnderTestField);
		Object classUnderTest;
		try {
			classUnderTest = classUnderTestField.get(testClass);

			if (classUnderTest == null) {
				throw new OtascoException("@ClassUnderTest must be initialize prior to calling OtascoAnnotations.init().  For info on how to use @ClassUnderTest and @Dependency see examples in Javadoc for OtascoAnnotations class.");
			}

			for (Field testField : dependencyFields) {

				String dependecyFieldName = testField.getName();
				final String value = testField.getAnnotation(Dependency.class).value();
				if (value != null && value.length() > 0) {
					dependecyFieldName = value;
				}

				final Field dependencyField = classUnderTest.getClass().getDeclaredField(dependecyFieldName);
				makeAccessible(dependencyField);
				makeAccessible(testField);
				dependencyField.set(classUnderTest, testField.get(testClass));
			}
		} catch (Exception e) {
			throw new OtascoException("Error processing class under test: " + e.getMessage(), e);
		}
	}

	public static void setField(final String fieldName, final Object value, final Object clazz) {
		try {
			final Field field = clazz.getClass().getField(fieldName);
			makeAccessible(field);
			field.set(clazz, value);
		} catch (Exception e) {
			throw new OtascoException("Error setting field on class: " + e.getMessage(), e);
		}
	}

	private static void makeAccessible(final Field cutField) {
		cutField.setAccessible(true);
	}
}