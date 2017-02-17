package ProjectWarehouse.ProjectWarehouse;

public class Position {
	private int x;
	private int y;
	private int z;
	public Position(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
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
	public void setXYZ(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	@Override
	public String toString() {
		return "Position=[x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setZ(int z) {
		this.z = z;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(!(obj instanceof Position)) {
			return false;
		}
		Position position = (Position) obj;
		return position.x == x && position.y == y && position.z == z;
	}
	@Override
	public int hashCode() {
		int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
	}
	
}
