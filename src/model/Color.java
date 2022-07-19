package model;

public enum Color {

	WHITE('W'),
	BLACK('B');

	Color(char c) {
		setChar(c);
	}
	
	private char color;
	
	private void setChar(char c) {
		this.color = c;
	}
	
	@SuppressWarnings("unused")
	private char getChar() {
		return this.color;
	}
}
