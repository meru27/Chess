package model.piece;

import model.Cell;
import model.Color;

public class Pawn extends AbstractPiece {

	Pawn(Color color) {
		super(color);
	}

	@Override
	public boolean validMove(Cell origin, Cell destination, boolean piecesBetween) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public char toChar() {
		return 'P';
	}

}
