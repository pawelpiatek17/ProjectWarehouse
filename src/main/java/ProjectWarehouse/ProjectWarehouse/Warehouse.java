package ProjectWarehouse.ProjectWarehouse;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


public class Warehouse {
	final Logger logger = Logger.getLogger(Warehouse.class);
	private String debugInfo;
	private int x;
	private int y;
	private int z;
	private StringBuilder history;
	private Crane crane;
	private HashMap<Long, Package> packagesMap;
	private ArrayList<Long> packageNumbersList;
	private Package field[][][];
	public Warehouse(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.packagesMap = new HashMap<Long, Package>();
		this.field = new Package[x][y][z];
		crane = new Crane();
		history = new StringBuilder("");
		packageNumbersList = new ArrayList<Long>();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}
	public StringBuilder getHistory() {
		return history;
	}
	
	public List<Package> getAllPackagesByType(ETypeOfPackage typeOfPackage) {
		List<Package> list = new ArrayList<Package>();
		Iterator<Entry<Long, Package>> iterator = packagesMap.entrySet().iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getValue().getTypeOfPackage() == typeOfPackage) {
				list.add(iterator.next().getValue());
			}
		}
		logger.debug("getAllPackagesByType: list size -> " + list.size());
		return list;
	}
	public Package getPackageByNumber(Long packageNumber) {
		history.delete(0, history.length());
		history.append("\n");
		Package package1 = packagesMap.get(packageNumber);
		if (package1 == null) {
			return null;
		}
		int positionX = package1.getPosition().getX();
		int positionY = package1.getPosition().getY();
		int positionZ = package1.getPosition().getZ();
		if (package1.isOnTop()) {
			field[positionX][positionY][positionZ] = null;
			packagesMap.remove(packageNumber);
			logger.debug("getPackageByNumber package is on top, returning");
			return package1;
		}
		int indexZ = z-1;
		Package packageToLift = field[positionX][positionY][indexZ];
		while (packageToLift == null) {
			indexZ--;
			packageToLift = field[positionX][positionY][indexZ];
			logger.debug("getPackageByNumber: package to lift" + packageToLift );
		}
		while(indexZ > -1 && packageToLift != package1) {
			Position positionForPackageToLift = findPositionForPackage(packageToLift);
			logger.debug("getPackageByNumber positionForPackageToLift = " + positionForPackageToLift);
			if (positionForPackageToLift == null) {
				return null;
			}
			debugInfo = String.valueOf(crane.liftPackage(packageToLift));
			logger.debug("getPackageByNumber crane.liftPackage result =  " + debugInfo);
			debugInfo = String.valueOf(crane.movePackage(positionForPackageToLift.getX(), positionForPackageToLift.getY(), positionForPackageToLift.getZ()));
			logger.debug("getPackageByNumber crane.movePackage result =  " + debugInfo);
			indexZ--;
			packageToLift = field[positionX][positionY][indexZ];
		}
		if (packageToLift == package1 && packageToLift.isOnTop()) {
			field[positionX][positionY][positionZ] = null;
			packagesMap.remove(package1.getPackageNumber());
			return package1;
		}
		return null;
	}
	
	private Position findPositionForPackage(Package packageToMove) {
		int priority = packageToMove.getPriority();
		Position position = packageToMove.getPosition();
		for(int i = 0; i < x; i++) {
			secondForLoop:
			for(int j = 0; j < y; j++) {
				for(int k = 0; k < z; k++) {
					if (position.getX() == i && position.getY() == j) {
						continue secondForLoop;
					}
					else if(k == 0 && field[i][j][k] == null) {
						return new Position(i, j, k);
					}
					else if (field[i][j][k] == null && field[i][j][k-1] != null && priority >= field[i][j][k-1].getPriority()) {
						return new Position(i, j, k);
					}
				}
			}
		}
		ArrayList<Position> blockedPositions = new ArrayList<Position>();
		blockedPositions.add(new Position(position.getX(), position.getY(), 0));
		logger.debug("findPositionForPackage blockedPositions = " + blockedPositions);
		Position positionFound = makePositionForPackage(packageToMove, blockedPositions);
		if (positionFound != null) {
			logger.debug("findPositionForPackage position found = " + positionFound);
			return new Position(positionFound.getX(), positionFound.getY(), positionFound.getZ());
		}
		return null;
	}
	
	private Position makePositionForPackage(Package packageToMove, ArrayList<Position> blockedPositions) {
		int priority = packageToMove.getPriority();
		Position position = packageToMove.getPosition();
		logger.debug("makePositionForPackage: start -> packageToMove = " + packageToMove);
		int counter = 0;
		HashMap<Position, Integer> hashMap = new HashMap<Position, Integer>();
		for(int i = 0; i < x; i++) {
			secondForLoop:
			for(int j = 0; j < y; j++) {
				counter = 0;
				for(int k = this.z-1; k >-1 ; k--) {
					if (blockedPositions.contains(new Position(i, j, 0))) {
						logger.debug("makePositionForPackage: creating hashMap -> blockedPosition = [" + i + "," + j + "," + k);
						continue secondForLoop;
					}
					if (field[i][j][k] != null && field[i][j][k].getPriority() <= priority) {
						Position p = new Position(i, j, k); 
						logger.debug("makePositionForPackage: creating hashMap -> " + p); 
						hashMap.put(p, counter);
						continue secondForLoop;
					}
					if(field[i][j][k] != null && field[i][j][k].getPriority() > priority) {
						counter++;
						logger.debug("makePositionForPackage: creating hashMap -> counter = " + counter); 
					}
				}
			}
		}
		logger.debug("makePositionForPackage: hashMap ->" + hashMap);
		Position choosenPosition;
		if(hashMap.isEmpty()) {
			return null;
		}
		else{
			choosenPosition = chooseBestPosition(hashMap);
			logger.debug("makePositionForPackage: hashMap -> choosenPosition = " + choosenPosition);
		}
		Package choosenPackage = field[choosenPosition.getX()][choosenPosition.getY()][choosenPosition.getZ()];
		blockedPositions.add(new Position(choosenPosition.getX(), choosenPosition.getY(), 0));
		int indexZ = this.z-1;
		Package packageToLift = field[choosenPosition.getX()][choosenPosition.getY()][indexZ];
		while (packageToLift == null) {
			indexZ--;
			packageToLift = field[choosenPosition.getX()][choosenPosition.getY()][indexZ];
		}
		boolean placeFound = false;
		while(indexZ > -1 && packageToLift != choosenPackage) {
			firstForLoop:
			for(int i = 0; i < this.x; i++) {
				secondForLoop:
				for(int j = 0; j < this.y; j++) {
					for(int k = 0; k < this.z; k++) {
						if (blockedPositions.contains(new Position(i, j, 0))) {
							logger.debug("makePositionForPackage: making place -> blockedPosition = [" + i + "," + j + "," + k);
							continue secondForLoop;
						}
						else if(k == 0 && field[i][j][k] == null) {
							debugInfo = String.valueOf(crane.liftPackage(packageToLift));
							logger.debug("makePositionForPackage: making place first else if -> crane.liftPackage = " + debugInfo);
							crane.liftedPackage.setOnTop(true);
							debugInfo = String.valueOf(crane.movePackage(i, j, k));
							logger.debug("makePositionForPackage: making place first else if -> crane.movePackage = " + debugInfo);
							placeFound  = true;
							break firstForLoop;
						}
						else if (k > 0 && field[i][j][k] == null && field[i][j][k-1] != null && packageToLift.getPriority() >= field[i][j][k-1].getPriority()) {
							debugInfo = String.valueOf(crane.liftPackage(packageToLift));
							logger.debug("makePositionForPackage: making place second else if -> crane.liftPackage = " + debugInfo);
							crane.liftedPackage.setOnTop(true);
							debugInfo = String.valueOf(crane.movePackage(i, j, k));
							logger.debug("makePositionForPackage: making place second else if -> crane.movePackage = " + debugInfo);
							placeFound  = true;
							break firstForLoop;
						}
					}
				}
			}
			if (!placeFound) {
				logger.debug("makePositionForPackage: !placeFound ");
				Position newBlockedPosition = new Position(packageToLift.getPosition().getX(), packageToLift.getPosition().getY(), packageToLift.getPosition().getZ());
				blockedPositions.add(newBlockedPosition);
				Position positionFound = makePositionForPackage(packageToLift,blockedPositions);
				blockedPositions.remove(newBlockedPosition); 
				if (positionFound != null) {
					logger.debug("makePositionForPackage: !placeFound -> positionFound = "  + positionFound);
					debugInfo = String.valueOf(crane.liftPackage(packageToLift));
					logger.debug("makePositionForPackage: !placeFound  -> crane.liftPackage = " + debugInfo);
					crane.liftedPackage.setOnTop(true);
					debugInfo = String.valueOf(crane.movePackage(positionFound.getX(),positionFound.getY(),positionFound.getZ()));
					logger.debug("makePositionForPackage: making place second else if -> crane.movePackage = " + debugInfo);
				} else {
					return null;
				}
			}
			indexZ--;
			packageToLift = field[choosenPosition.getX()][choosenPosition.getY()][indexZ];
			logger.debug("makePositionForPackage: making place next loop -> packageToLift = " + packageToLift);
		}
		if (packageToLift == choosenPackage) {
			if((choosenPosition.getZ()+1) == this.z) {
				return null;
			}
			logger.debug("makePositionForPackage packageToLift == choosenPackage -> " + packageToLift);
			return new Position(choosenPosition.getX(), choosenPosition.getY(), choosenPosition.getZ()+1);
		}
		return null;
	}
	private Position chooseBestPosition(HashMap<Position, Integer> hashMap) {
		Iterator<Entry<Position, Integer>> iterator = hashMap.entrySet().iterator();
		Entry<Position, Integer> pairMax = (Entry<Position, Integer>) iterator.next();
		while (iterator.hasNext()) {
			Entry<Position, Integer> pair = (Entry<Position, Integer>) iterator.next();
			if (pair.getValue() < pairMax.getValue()) {
				pairMax = pair;
			}
		}
		return pairMax.getKey();
	}
	public String addPackage(ETypeOfPackage typeOfPackage, String description, long packageNumber, int priority, int _x ,int _y) {
		if ((_x >= this.x) || (_y >= this.y)) { 
			return "Give correct position";
		}
		int x = _x;
		int y = _y;
		int z = this.z;
		Date date = new Date();
		int numberOfShifts = 0;
		int positionZ = 0;
		boolean add = false;
		if (packageNumbersList.contains(packageNumber)) {
			return "Package number was used before";
		}
		for(int i = 0; i < z; i++) {
			if(i > 0 && field[x][y][i-1].getPriority() > priority) {
				return "Cant put package with lesser priority on top of the package with higher priority, consider getting some packages off";
			}
			if (i == 0 && field[x][y][i] == null) {
				positionZ = i;
				add = true;
				break;
			}
			else if (field[x][y][i] == null) {
				positionZ = i;
				add = true;
				field[x][y][i-1].setOnTop(false);
				break;
			}
		}
		if(!add) {
			return "Warehouse is full";
		}
		Package package1 = new Package(typeOfPackage, description, date, numberOfShifts, packageNumber, priority, x, y, positionZ);
		packagesMap.put(packageNumber, package1);
		packageNumbersList.add(packageNumber);
		field[x][y][positionZ] = package1;
		package1.setOnTop(true);
		return "Success";
	}
	private class Crane {
		private Package liftedPackage;
		public boolean liftPackage(Package package1) {
			if (liftedPackage == null && package1.isOnTop()) {
				liftedPackage = package1;
				logger.debug("Crane liftedPackage -> " + liftedPackage);
				return true;
			}
			return false;
		}
		private boolean movePackage(int x,int y, int z) {
			logger.debug("Crane movePackage -> field = " +  field[x][y][z] + " -> [" + x + "," + y + "," + z + "]");
			if (field[x][y][z] == null && liftedPackage != null) {
				field[liftedPackage.getPosition().getX()][liftedPackage.getPosition().getY()][liftedPackage.getPosition().getZ()] = null;
				Position debugPosition = new Position(liftedPackage.getPosition().getX(), liftedPackage.getPosition().getY(), liftedPackage.getPosition().getZ());
				history.append("Moved package nr " + crane.liftedPackage.getPackageNumber() + " from " + crane.liftedPackage.getPosition().toString() +
						" to [x=" + x + ", y=" + y + ", z=" + z + "]\n" );
				if (liftedPackage.getPosition().getZ() != 0) { 
					field[liftedPackage.getPosition().getX()][liftedPackage.getPosition().getY()][liftedPackage.getPosition().getZ()-1].setOnTop(true);
				}
				liftedPackage.setPosition(x, y, z);
				liftedPackage.setNumberOfShifts();
				liftedPackage.setOnTop(true);
				if (z != 0) {
					field[x][y][z-1].setOnTop(false);
				}
				packagesMap.put(liftedPackage.getPackageNumber(), liftedPackage);
				field[x][y][z] = liftedPackage;
				liftedPackage = null;
				logger.debug("Crane movePackage -> empited field = " + field[debugPosition.getX()][debugPosition.getY()][debugPosition.getZ()]);
				return true;
			}
			return false;
		}
	}
}
