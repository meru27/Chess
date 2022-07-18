package model.piece;

import model.Cell;
import model.Color;

public interface Piece {
	Color color = null;
	public boolean firstMoveAvailable();
	public void useFirstMove();
	public boolean validMove(Cell origin, Cell destination, boolean piecesBetween);
	public void setCell(Cell c);
	public Cell getCell();
	public Color getColor();
	public char toChar();
}
