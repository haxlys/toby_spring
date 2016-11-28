package chapter02.learningtest.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class JUnitTest { 
	/**
	 * JUnit은 테스트 메소드를 수행할 때마다 새로운 오브젝트를 만든다고 했다.
	 * 이러한 사실을 테스트로 확인해보자.
	 * 
	 * 참고로 이클립스에서 static 메소드의 import 단축키는 Ctrl + Shift + M
	 */
	static JUnitTest testObject;
	
	@Test public void test1() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
	
	@Test public void test2() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
	
	@Test public void test3() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
	/**
	 * 만약 첫번쨰와 세번째 오브젝트가 같을 경우에는 지금 테스트가 무용지물일 것이다.
	 * 다음예제에서 고쳐보자
	 */
}
