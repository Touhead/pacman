package Model;

/**
 * Define the different move possible.
 * @author Touhead
 *
 */
public enum Move {

	RIGTH,
	UP,
	LEFT,
	DOWN;
	
	/**
	 * Return the inverse of this position.
	 * @return
	 */
	public Move inverse(){
		
		if (this.equals(Move.RIGTH))
			return Move.LEFT;
		if (this.equals(Move.UP))
			return Move.DOWN;
		if (this.equals(Move.LEFT))
			return Move.RIGTH;
		if (this.equals(Move.DOWN))
			return Move.UP;
		
		return null;
	}
	
	/**
	 * Return the position that match the String parameter.
	 * @param move
	 * @return the move according the ASCII code in parameter
	 */
	public static Move getMoveFromASCII(String move){
		
		if ((move.equals("65"))||(move.equals("81"))||(move.equals("37")))
			return Move.LEFT;
		if ((move.equals("83"))||(move.equals("40")))
			return Move.DOWN;
		if ((move.equals("68"))||(move.equals("39")))
			return Move.RIGTH;
		if ((move.equals("87"))||(move.equals("90"))||(move.equals("38")))
			return Move.UP;
		
		return null;
	}
}
