package com.brightdome.otasco.runners;

import java.lang.reflect.InvocationTargetException;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;


public class OtascoMockitoJUnitRunner extends Runner {
	
	private Runner runner;
	
	@SuppressWarnings("deprecation")
    public OtascoMockitoJUnitRunner(Class<?> clazz) throws InvocationTargetException, InitializationError, org.junit.internal.runners.InitializationError {
		runner = RunnerFactory.create(clazz);
	}

	@Override
	public Description getDescription() {
		return runner.getDescription();
	}

	@Override
	public void run(RunNotifier notifier) {
		runner.run(notifier);
	}
}