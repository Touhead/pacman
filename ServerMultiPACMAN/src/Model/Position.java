package Model;

/**
 * Define a Position in tile.
 * @author Touhead
 *
 */
public class Position extends PixelPosition{
	
	public Position(int x, int y) {
		super(x*8+4, y*8+4);
	}
	
	public Position(Position pos) {
		super(pos.getPixelX(), pos.getPixelY());
	}

	public static int getLength() {
		return PixelPosition.getLength()/8;
	}

	public static int getWidth() {
		return PixelPosition.getWidth()/8;
	}
	
	/**
	 * Return the x coordinate in tile.
	 */
	public int getX() {
		return ((Math.round(super.getX()/8 - (super.getX()%8 < 2 ? 1 : 0)))+Position.getLength())%Position.getLength();
	}
	
	public int getPixelX() {
		return super.getX();
	}
	
	/**
	 * Set the x coordinate with a x coordinate in tile (in the middle of the tile).
	 */
	public boolean setX(int x) {
		boolean bool = !((x < 0)||(x > Position.getLength()-1));
		x = (x+Position.getLength())%Position.getLength();
		super.setX(x*8+4);
		return bool;
	}
	
	public boolean setPixelX(int x) {
		return super.setX(x);
	}
	
	/**
	 * Return the y coordinate in tile.
	 */
	public int getY() {
		return ((Math.round(super.getY()/8 - (super.getY()%8 < 2 ? 1 : 0)))+Position.getWidth())%Position.getWidth();
	}
	
	public int getPixelY() {
		return super.getY();
	}
	
	/**
	 * Set the y coordinate with a x coordinate in tile (in the middle of the tile).
	 */
	public boolean setY(int y) {
		boolean bool = !((y < 0)||(y > Position.getLength()-1));
		y = (y+Position.getWidth())%Position.getWidth();
		super.setY(y*8+4);
		return bool;
	}
	
	public boolean setPixelY(int y) {
		return super.setY(y);
	}
	
	public static double euclideanDistance(Position p1, Position p2){
		
		return Math.sqrt((p1.getX() - p2.getX())*(p1.getX() - p2.getX()) + (p1.getY() - p2.getY())*(p1.getY() - p2.getY()));
	}
	
	/**
	 * Return the distance between 2 positions.
	 * @param p1
	 * @param p2
	 * @return the euclidean distance between p1 and p2
	 */
	public static double euclideanDistancePixel(Position p1, Position p2){
		
		return Math.sqrt((p1.getPixelX() - p2.getPixelX())*(p1.getPixelX() - p2.getPixelX()) + (p1.getPixelY() - p2.getPixelY())*(p1.getPixelY() - p2.getPixelY()));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getX();
		result = prime * result + getY();
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
		Position other = (Position) obj;
		if (getX() != other.getX())
			return false;
		if (getY() != other.getY())
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Position [x=" + getX() + ", y=" + getY() + "]";
	}
}
