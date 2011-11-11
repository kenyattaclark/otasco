package com.brightdome.testutil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Otasco {

	public static void initClassUnderTest(Object testClass) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		if (testClass == null) {
			throw new OtascoException("testClass cannot be null. For info how to use @Mock annotations see examples in javadoc for MockitoAnnotations class");
	    }
				
		final List<Field> dependencyFields = new ArrayList<Field>();
		Field classUnderTestField = null;
		
		for (Field field : testClass.getClass().getDeclaredFields()) {
			final TestDependency testDependency = field.getAnnotation(TestDependency.class);
			final ClassUnderTest cutAnnotation = field.getAnnotation(ClassUnderTest.class);
			if (testDependency != null) {
				dependencyFields.add(field);
			} else if (cutAnnotation != null) {
				classUnderTestField = field;
			}
		}
		
		if (classUnderTestField == null) {
			throw new OtascoException("testClass cannot be null. For info how to use @Mock annotations see examples in javadoc for MockitoAnnotations class");
		}
		
		classUnderTestField.setAccessible(true);
		Object classUnderTest = classUnderTestField.get(testClass);
		
		for (Field field : dependencyFields) {
			Field cutField = classUnderTest.getClass().getDeclaredField(field.getName());
			cutField.setAccessible(true);
			field.setAccessible(true);
			cutField.set(classUnderTest, field.get(testClass));
		}
	}
}