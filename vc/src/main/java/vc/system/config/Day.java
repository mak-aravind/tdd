package vc.system.config;



public enum Day {
	ONE(0),TWO(1),THREE(2),FOUR(3),FIVE(4);
	
	private int index;
	
	private Day(int index) { this.index = index; }

	public int getIndex(){
		return index;
	}
}
