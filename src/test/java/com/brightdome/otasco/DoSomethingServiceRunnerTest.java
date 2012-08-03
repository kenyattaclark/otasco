package com.brightdome.otasco;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.brightdome.otasco.runners.OtascoMockitoJUnitRunner;

@RunWith(OtascoMockitoJUnitRunner.class)
public class DoSomethingServiceRunnerTest {

	@ClassUnderTest
	private DoSomethingService doSomethingService;

	@Mock
	@Dependency
	private DependencyA dependencyA;

	@Mock
	@Dependency
	private DependencyB dependencyB;

	@Test
	public void testOtasco() {
		Mockito.when(dependencyA.doSomething()).thenReturn("I did something with mock DependencyA");
		Mockito.when(dependencyB.doSomething()).thenReturn("I did something with mock DependencyB");
		doSomethingService.doSomething();
	}
}