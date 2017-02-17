package ProjectWarehouse.ProjectWarehouse;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;


public class Main 
{
    public static void main( String[] args )
    {
    	final Logger logger = Logger.getLogger(Main.class);
        Warehouse warehouse = new Warehouse(20, 20, 20);
        Random random = new Random();
        Random randomX = new Random();
        Random randomY = new Random();
        for(long i = 0; i < 20000; i++){
        	int p = random.nextInt(3)+1;
        	int x = randomX.nextInt(20);
        	int y = randomY.nextInt(20);
        	logger.debug(warehouse.addPackage(ETypeOfPackage.toys, "description", i, p, x, y));
        }
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
        logger.debug(warehouse.getPackageByNumber(17L));
        logger.debug(warehouse.getPackageByNumber(4L));
        //List<Package> list = warehouse.getAllPackagesByType(ETypeOfPackage.toys);
        //logger.debug(list);
        logger.debug(warehouse.getHistory());  
    }
}
