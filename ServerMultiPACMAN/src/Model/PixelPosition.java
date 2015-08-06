package Model;

/**
 * Define a Position in pixel.
 * @author Touhead
 *
 */
public class PixelPosition{
	
	static private int length;
	static private int width;
	private int x;
	private int y;
	
	public PixelPosition(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public PixelPosition(PixelPosition pos) {
		super();
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		PixelPosition.length = length*8;
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		PixelPosition.width = width*8;
	}

	public int getX() {
		return x;
	}

	public boolean setX(int x) {
		this.x = (x+length)%length;
		return (x < 0)&&(x > length-1);
	}

	public int getY() {
		return y;
	}


	public boolean setY(int y) {
		this.y = (y+width)%width;
		return (y < 0)&&(y > width-1);
	}

	/**
	 * Return the distance between 2 positions.
	 * @param p1
	 * @param p2
	 * @return the euclidean distance between p1 and p2
	 */
	public static double euclideanDistance(PixelPosition p1, PixelPosition p2){
		
		return Math.sqrt((p1.getX() - p2.getX())*(p1.getX() - p2.getX()) + (p1.getY() - p2.getY())*(p1.getY() - p2.getY()));
	}

	/**
	 * A movable object can move only if it's on a specific position in the tile.
	 * @param move
	 * @return true if we can move, false otherwise
	 */
	public boolean isMovable(Move move){
		
		if (move.equals(Move.RIGTH))
			return ((y%8 == 4));
		else if (move.equals(Move.LEFT))
			return ((y%8 == 4));
		else if (move.equals(Move.UP))
			return ((x%8 == 4));
		else if (move.equals(Move.DOWN))
			return ((x%8 == 4));
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PixelPosition other = (PixelPosition) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PixelPosition [x=" + x + ", y=" + y + "]";
	}
}
