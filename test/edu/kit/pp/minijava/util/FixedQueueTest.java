package edu.kit.pp.minijava.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FixedQueueTest {

	private FixedQueue<Integer> _queue;

	public FixedQueueTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		_queue = new FixedQueue<Integer>(3);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void addShouldAddItemsToTheBeginning() {
		_queue.push(4);
		assertEquals(4, (int)_queue.get(0));
		_queue.push(5);
		assertEquals(5, (int)_queue.get(0));
	}

	@Test
	public void addShouldMoveItemsToTheEnd() {
		_queue.push(4);
		_queue.push(5);
		assertEquals(4, (int)_queue.get(1));
	}

	@Test(expected=Exception.class)
	public void shouldNotSaveMoreThan3Items() {
		_queue.push(4);
		_queue.push(5);
		_queue.push(6);
		_queue.push(7);
		_queue.get(3);
	}

	@Test
	public void addShouldKickOldValuesOut() {
		_queue.push(4);
		_queue.push(5);
		_queue.push(6);
		_queue.push(7);
		assertEquals(5, (int)_queue.get(2));
	}
}
