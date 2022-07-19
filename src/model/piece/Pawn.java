package model.piece;


import model.Cell;
import model.Color;
import util.Direction;

public class Pawn extends AbstractPiece {
	

	Pawn(Color color) {
		super(color);
	}
	
	@Override
	public boolean validMove(Cell origin, Direction dir, boolean piecesBetween) {
		
		if (piecesBetween) 
			return false;
		
		
		return false;
	}

	@Override
	public char toChar() {
		return 'P';
	}

}
