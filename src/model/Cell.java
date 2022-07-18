package model;

import model.piece.Piece;

public class Cell {

	private int row;
	private int col;
	private Piece piece = null;

	public Cell(int y, int x) {
		this.row = y;
		this.col = x;
	}

	public void removePiece() {
		this.piece = null;
	}

	public void setPiece(Piece p) {
		this.piece = p;
	}
	
	public boolean isEmpty() {
		return (this.piece == null);
	}
	
	public Color getPieceColor() {
		if (this.piece != null)
			return this.piece.getColor();
		return null;
	}
	
	public int getColumn() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public boolean equalCoords(Cell c) {
		return (this.row == c.row && this.col == c.col);
	}
	
	public String toString() {
		return "";
	}

}
