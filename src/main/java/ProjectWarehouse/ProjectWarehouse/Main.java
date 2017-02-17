package ProjectWarehouse.ProjectWarehouse;

import org.apache.log4j.Logger;


public class Main 
{
    public static void main( String[] args )
    {
    	final Logger logger = Logger.getLogger(Main.class);
        Warehouse warehouse = new Warehouse(1, 5, 5);
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 1L, 1, 1, 1));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 2L, 1, 1, 1));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 3L, 2, 1, 1));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 4L, 3, 1, 1));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 5L, 1, 1, 2));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 6L, 1, 1, 2));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 7L, 2, 1, 2));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 8L, 3, 1, 2));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 9L, 3, 1, 2));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 10L, 1, 1, 3));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 11L, 2, 1, 3));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 12L, 3, 1, 3));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 13L, 3, 1, 3));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 14L, 1, 1, 4));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 15L, 2, 1, 4));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 16L, 3, 1, 4));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 17L, 1, 1, 5));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 18L, 3, 1, 5));
        logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", 19L, 3, 1, 5));
        logger.debug(warehouse.getPackageByNumber(1L));
        logger.debug(warehouse.getHistory());
        
    }
}
