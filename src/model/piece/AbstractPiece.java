package model.piece;

import model.Cell;
import model.Color;
import util.Direction;

public abstract class AbstractPiece implements Piece{
	
	private boolean firstMoveAvailable;
	private Color color;
	private Cell cell;
	
	AbstractPiece(Color color){
		this.firstMoveAvailable = true;
		this.color = color;
	}

	@Override
	public boolean firstMoveAvailable() {
		return this.firstMoveAvailable;
	}

	@Override
	public void useFirstMove() {
		this.firstMoveAvailable = false;
	}

	@Override
	public abstract boolean validMove(Cell origin, Direction dir, boolean piecesBetween);

	@Override
	public void setCell(Cell c) {
		this.cell = c;
	}

	@Override
	public Cell getCell() {
		return this.cell;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public abstract char toChar();
	

}
