package ProjectWarehouse.ProjectWarehouse;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WarehouseTest {
	Warehouse warehouse;
	String correctPosition;
	String packageNumberUsedBefore;
	String lesserPriorityOnTop;
	String warehouseFull;
	String success;
	
	@Before
	public void setUp() throws Exception {
		warehouse =  new Warehouse(1, 2, 2);
		correctPosition = "Give correct position";
		packageNumberUsedBefore = "Package number was used before";
		lesserPriorityOnTop = "Cant put package with lesser priority on top of the package with higher priority, consider getting some packages off";
		warehouseFull = "Warehouse is full";
		success = "Success";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addPackageTest() {
		assertTrue(warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 3, 3, 12, 12).equals(correctPosition));
		warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 3, 3, 0, 0);
		assertTrue(warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 3, 3, 0, 0).equals(packageNumberUsedBefore));
		assertTrue(warehouse.addPackage(ETypeOfPackage.carParts, "sad", 1, 1, 0, 0).equals(lesserPriorityOnTop));
		warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 9, 3, 0, 0);
		assertTrue(warehouse.addPackage(ETypeOfPackage.carParts, "sad", 6, 3, 0, 0).equals(warehouseFull));
		
	}
	@Test
	public void getPackageByNumberTest() {
		assertTrue(warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 1, 1, 0, 0).equals(success));
		assertTrue(warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 2, 1, 0, 0).equals(success));
		assertTrue(warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 3, 2, 0, 1).equals(success));
		assertTrue(warehouse.addPackage(ETypeOfPackage.carParts, "sad", 4, 2, 0, 1).equals(success));
		assertTrue(warehouse.getPackageByNumber(8L) == null);
		assertTrue(warehouse.getPackageByNumber(1L) == null);
		assertTrue(warehouse.getPackageByNumber(2L) != null);
		assertTrue(warehouse.getPackageByNumber(4L) != null);
		
	}
	
	public void getAllPackagesByTypeTest() {
		assertTrue(warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 1, 1, 0, 0).equals(success));
		assertTrue(warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 2, 1, 0, 0).equals(success));
		assertTrue(warehouse.addPackage(ETypeOfPackage.furnitures, "abc", 3, 2, 0, 1).equals(success));
		assertTrue(warehouse.addPackage(ETypeOfPackage.carParts, "sad", 4, 2, 0, 1).equals(success));
		assertTrue(warehouse.getAllPackagesByType(ETypeOfPackage.furnitures).size() == 3);
	}
}
