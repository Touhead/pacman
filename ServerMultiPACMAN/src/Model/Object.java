package Model;

/**
 * Define an object.
 * @author Touhead
 *
 */
public abstract class Object {

	private Position pos;

	public Object(Position pos) {
		super();
		this.pos = pos;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
}
