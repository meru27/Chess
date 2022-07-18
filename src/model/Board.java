package model;

import java.util.ArrayList;
import java.util.List;

import model.piece.Piece;

public class Board {
	private ArrayList<ArrayList<Cell>> board;
	private final int ROWS = 8;
	private final int COLS = 8;

	public Board() {
		board = new ArrayList<ArrayList<Cell>>(ROWS);
		for (@SuppressWarnings("unused")
		ArrayList<Cell> row : board) {
			row = new ArrayList<Cell>(COLS);
		}

		for (int i = 0; i < board.size(); ++i) {
			for (int j = 0; j < board.get(i).size(); ++j) {
				board.get(i).add(j, new Cell(i, j));
			}
		}
	}

	private boolean validCoords(int row, int col) {
		if (row < 0 || row >= ROWS || col < 0 || col >= COLS)
			return false;
		return true;
	}

	private boolean validCoords(Cell c) {
		for (ArrayList<Cell> row : board) {
			if (row.contains(c))
				return true;
		}
		return false;
	}

	public void set(Piece p, Cell c) throws IncorrectCoordsExcep {
		if (validCoords(c)) {
			c.setPiece(p);
			p.setCell(c);
		} else
			throw new IncorrectCoordsExcep("Wrong coordinates / cell");
	}

	public void set(Piece p, int row, int col) throws IncorrectCoordsExcep {
		if (validCoords(row, col)) {
			this.board.get(row).get(col).setPiece(p);
			p.setCell(this.board.get(row).get(col));
		} else
			throw new IncorrectCoordsExcep("Wrong coordinates / cell");
	}

	public Cell getCell(int row, int col) throws IncorrectCoordsExcep {
		if (validCoords(row, col))
			return board.get(row).get(col);
		else
			throw new IncorrectCoordsExcep("Bad coordinates");
	}

	public List<Cell> getCells() {
		List<Cell> newList = new ArrayList<Cell>();
		for (ArrayList<Cell> row : board) {
			for (Cell c : row) {
				newList.add(c);
			}
		}
		return newList;
	}

	public int getNumberOfRows() {
		return this.ROWS;
	}

	public int getNumberOfCols() {
		return this.COLS;
	}

	public int getNumberOfPieces(Color col) {
		int count = 0;
		for (ArrayList<Cell> row : board) {
			for (Cell c : row) {
				Color color = c.getPieceColor();
				if (color != null && color == col)
					++count;
			}
		}
		return count;
	}

	public Cell getCellForAlgebraicNotation(String coord) {
		int row = coord.charAt(0) - 97;
		int col = coord.charAt(1) - 1;

		return board.get(row).get(col);
	}

	public String getCoordsInAlgebraicNotation(Cell c) throws IncorrectCoordsExcep {
		if (validCoords(c)) {
			char row = (char) (c.getRow() + 97);
			char col = (char) (c.getColumn() - 1);
			return "" + row + col;
		} else
			throw new IncorrectCoordsExcep("Wrong cell");
	}

	public List<Cell> getCellsInBetween(Cell origin, Cell target) throws IncorrectCoordsExcep {
		if (!validCoords(origin) || !validCoords(target))
			throw new IncorrectCoordsExcep("Invalid cells");		
		int y = origin.getRow();
		int x = origin.getColumn();
		List<Cell> newList = new ArrayList<Cell>();
		
		for (int i = y; i < board.size() ; ++i) {
			while (board.get(i).get(x) != target && x < board.get(i).size()) {
				newList.add(board.get(i).get(x));
				++x;
			}
			if (x == board.get(i).size() - 1)
				x = 0;
			else
				return newList;
		}
		return newList;
	}
	
	public String toString() {
		return "";
	}

}
