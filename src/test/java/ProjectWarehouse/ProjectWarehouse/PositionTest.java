package ProjectWarehouse.ProjectWarehouse;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PositionTest {
	
	Position position;
	Position position2;
	ArrayList<Position> blockedPositions;
	
	@Before
	public void setUp() throws Exception {
		position = new Position(2, 2, 2);
		position2 = new Position(2, 2, 2);
		blockedPositions = new ArrayList<Position>();
		blockedPositions.add(position);
		blockedPositions.add(position2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEqualsObject() {
		assertTrue(position.equals(position2));
		assertTrue(blockedPositions.contains(position2));
		assertTrue(blockedPositions.contains(position));
	}

}
