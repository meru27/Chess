package util;

public enum Direction {
	
	/** Northeast. */
	DIAGONAL_NE(-1, +1),
	
	/** Northwest. */
	DIAGONAL_NW(-1, -1),
	
	/** South east. */
	DIAGONAL_SE(+1, +1),
	
	/** South west. */
	DIAGONAL_SW(+1, -1),
	
	/** North. */
	VERTICAL_N(-1, 0),
	
	/** South. */
	VERTICAL_S(+1, 0),
	
	/** East. */
	HORIZONTAL_E(0, +1),
	
	/** West. */
	HORIZONTAL_W(0, -1);
	
	
	private int rows;
	private int cols;
	
	private Direction(int rows, int cols) {
		this.setRows(rows);
		this.setCols(cols);
	}
	
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	private void setRows(int rows) {
		this.rows = rows;
	}
	
	private void setCols(int cols) {
		this.cols = cols;
	}

}
