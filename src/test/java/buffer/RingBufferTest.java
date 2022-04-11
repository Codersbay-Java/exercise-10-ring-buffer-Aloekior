package buffer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RingBufferTest {

	private static final int RING_BUFFER_CAPACITY = 3;
	private RingBuffer ringBuffer;

	@BeforeEach
	void setUp() {
		ringBuffer = new RingBuffer(RING_BUFFER_CAPACITY);
	}

	@ParameterizedTest
	@MethodSource("provideDifferentStringCapacties")
	void testCapacity(String... bufferElements) {
		setUpBuffer(bufferElements);
		assertEquals(RING_BUFFER_CAPACITY, ringBuffer.capacity());
	}

	@ParameterizedTest
	@MethodSource("provideDifferentSizes")
	void testSize(int referenceSize, String... bufferElements) {
		setUpBuffer(bufferElements);
		assertEquals(referenceSize, ringBuffer.size());
	}

	@Test
	void testIsEmpty() {
		assertTrue(ringBuffer.isEmpty());
		ringBuffer.enqueue("a");
		assertFalse(ringBuffer.isEmpty());
	}

	@Test
	void testIsFull() {
		setUpBuffer("1", "2", "3");
		assertTrue(ringBuffer.isFull());
	}

	@ParameterizedTest
	@MethodSource("provideDifferentSizes")
	void testEnqueue(int referenceSize, String... bufferElements) {
		for (int i = 0; i < bufferElements.length; i++) {
			ringBuffer.enqueue(bufferElements[i]);
			if (i > RING_BUFFER_CAPACITY) {
				// if we reach the max capacity of the buffer the first element gets overridden
				assertEquals(bufferElements[i], ringBuffer.peek());
			} else {
				assertEquals(bufferElements[i], getLastBufferElement());
			}
		}
	}

	@Test
	void testPeek() {
		// TODO: implement this test yourself
		fail("Not yet implemented");
	}

	private static Stream<Arguments> provideDifferentSizes() {
		return Stream.of(Arguments.of(1, (Object) new String[] { "1" }),
				Arguments.of(2, (Object) new String[] { "1", "2" }),
				Arguments.of(RING_BUFFER_CAPACITY, (Object) new String[] { "1", "2", "3" }),
				Arguments.of(RING_BUFFER_CAPACITY, (Object) new String[] { "1", "2", "3", "4" }),
				Arguments.of(RING_BUFFER_CAPACITY, (Object) new String[] { "1", "2", "3", "4", "5" }),
				Arguments.of(RING_BUFFER_CAPACITY, (Object) new String[] { "1", "2", "3", "4", "5", "6" }),
				Arguments.of(RING_BUFFER_CAPACITY, (Object) new String[] { "1", "2", "3", "4", "5", "6", "7" }),
				Arguments.of(RING_BUFFER_CAPACITY, (Object) new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
	}
	//@formatter:on

	private static Stream<Arguments> provideDifferentStringCapacties() {
		//@formatter:off
		return Stream.of(
				Arguments.of((Object) new String[] { null }),
				Arguments.of((Object) new String[] { "1", "2" }),
				Arguments.of((Object) new String[] { "1", "2", "3" }),
				Arguments.of((Object) new String[] { "1", "2", "3", "4" }),
				Arguments.of((Object) new String[] { "" })
			);
	}
	//@formatter:on

	private void setUpBuffer(String... bufferElements) {
		for (String element : bufferElements) {
			ringBuffer.enqueue(element);
		}
	}

	private String getLastBufferElement() {
		String element = "";
		Iterator<String> iterator = ringBuffer.iterator();
		String nextElement;
		while (iterator.hasNext()) {

			nextElement = iterator.next();
			if (nextElement != null) {
				element = nextElement;
			}
		}

		return element;
	}

}
