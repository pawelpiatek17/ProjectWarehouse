package ProjectWarehouse.ProjectWarehouse;

import java.util.Date;

public class Package {
	private ETypeOfPackage typeOfPackage;
	private String description;
	private Date date;
	private int numberOfShifts;
	private long packageNumber;
	private int priority;
	private Position position;
	private boolean isOnTop;
	
	public Package(ETypeOfPackage typeOfPackage, String description, Date date, int numberOfShifts, long packageNumber,
			int priority, int x ,int y, int z) {
		super();
		this.typeOfPackage = typeOfPackage;
		this.description = description;
		this.date = date;
		this.numberOfShifts = numberOfShifts;
		this.packageNumber = packageNumber;
		this.priority = priority;
		this.position = new Position(x, y, z);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfShifts() {
		return numberOfShifts;
	}

	public void setNumberOfShifts() {
		this.numberOfShifts += 1;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(int x, int y, int z) {
		this.position.setXYZ(x, y, z);
	}

	public ETypeOfPackage getTypeOfPackage() {
		return typeOfPackage;
	}

	public Date getDate() {
		return date;
	}

	public long getPackageNumber() {
		return packageNumber;
	}

	public int getPriority() {
		return priority;
	}

	public boolean isOnTop() {
		return isOnTop;
	}

	public void setOnTop(boolean isOnTop) {
		this.isOnTop = isOnTop;
	}

	@Override
	public String toString() {
		return "Package [typeOfPackage=" + typeOfPackage + ", description=" + description + ", date=" + date
				+ ", numberOfShifts=" + numberOfShifts + ", packageNumber=" + packageNumber + ", priority=" + priority
				+ ", "+ position.toString() + ", isOnTop=" + isOnTop + "]";
	}
	
}
