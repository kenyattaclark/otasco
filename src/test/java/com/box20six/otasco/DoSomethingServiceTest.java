package com.box20six.otasco;
import org.junit.Before;
import org.junit.Test;


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