package com.brightdome.otasco;
import org.junit.Before;
import org.junit.Test;

import com.brightdome.otasco.ClassUnderTest;
import com.brightdome.otasco.Dependency;
import com.brightdome.otasco.DependencyA;
import com.brightdome.otasco.DependencyB;
import com.brightdome.otasco.DoSomethingService;
import com.brightdome.otasco.Otasco;



public class DoSomethingServiceTest {

	@ClassUnderTest
	private DoSomethingService doSomethingService;

	@Dependency
	private DependencyA dependencyA;

	@Dependency
	private DependencyB dependencyB;

	@Before
	public void init() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		dependencyA = new DependencyA();
		dependencyB = new DependencyB();
		doSomethingService = new DoSomethingService();
		Otasco.init(this);
	}

	@Test
	public void testOtasco() {
		doSomethingService.doSomething();
	}
}