import org.junit.Before;
import org.junit.Test;

import com.brightdome.testutil.ClassUnderTest;
import com.brightdome.testutil.DependencyA;
import com.brightdome.testutil.DependencyB;
import com.brightdome.testutil.DoSomethingService;
import com.brightdome.testutil.Otasco;
import com.brightdome.testutil.TestDependency;


public class DoSomethingServiceTest {

	@ClassUnderTest
	private DoSomethingService doSomethingService;
	
	@TestDependency
	private DependencyA dependencyA;
	
	@TestDependency
	private DependencyB dependencyB;
	
	@Before
	public void init() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		dependencyA = new DependencyA();
		dependencyB = new DependencyB();
		doSomethingService = new DoSomethingService();
		Otasco.initClassUnderTest(this);
	}
	
	@Test
	public void testOtasco() {
		doSomethingService.doSomething();
	}
}