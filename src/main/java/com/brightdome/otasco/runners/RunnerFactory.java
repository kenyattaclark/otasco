package com.brightdome.otasco.runners;

import java.lang.reflect.Field;

import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.mockito.MockitoAnnotations;

import com.brightdome.otasco.Otasco;
import com.brightdome.otasco.OtascoException;

@SuppressWarnings("deprecation")
public class RunnerFactory {
	
	private static boolean jUnit45OrHigher;
	
	static {
		try {
			Class.forName("org.junit.runners.BlockJUnit4ClassRunner");
			jUnit45OrHigher = true;
		} catch (Throwable t) {
			jUnit45OrHigher = false;
		}
	}
	
	public static Runner create(Class<?> clazz) throws InitializationError, org.junit.internal.runners.InitializationError {
		if (jUnit45OrHigher) {
			return new BlockJUnit4ClassRunner(clazz) {
                protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
	                initMocks(target);
	                return super.withBefores(method, target, statement);
	            }
	        };
		} else {
			return new JUnit4ClassRunner(clazz) {
	            @Override
	            protected Object createTest() throws Exception {
	                final Object test = super.createTest();
	                initMocks(test);
	                return test;
	            }
	        };
		}
	}
	
	protected static void initClassUnderTest(Object target) {
        Field classUnderTestField = Otasco.retrieveClassUnderTest(target);
        try {
            classUnderTestField.set(target, classUnderTestField.getType().newInstance());
        } catch (Exception e) {
            throw new OtascoException("When using OtascoMockitoJUnitRunner @ClassUnderTest must have no argument constructor otherwise use Otasco.init().");
        }
    }

    protected static void initMocks(Object target) {
        // init class under test
        initClassUnderTest(target);
        // init annotated mocks before tests
        MockitoAnnotations.initMocks(target);
        Otasco.init(target);
    }
}