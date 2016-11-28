package chapter02.learningtest.junit;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class JUnitTest2 {
	/**
	 * 모든 테스트를 비교하여 같은지 아닌지 확인한다.
	 * 이전 예제의 빈틈을 잘 해결하였다.
	 */
	static Set<JUnitTest2> testObjects = new HashSet<JUnitTest2>();
	
	@Test public void test1() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
	}
	
	@Test public void test2() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
	}
	
	@Test public void test3() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
	}
}
