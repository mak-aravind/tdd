package vc.common.model;



public enum Direction {
	NORTH(2),SOUTH(4);
	
	private int minReadings;
	
	private Direction(int minReadings) { this.minReadings = minReadings; }
    
	public static Direction fromListSize(int size) {
        for (Direction direction: Direction.values()) {
            if (direction.minReadings == size) {
                return direction;
            }
        }
        return null;
    }
	public int getMinimumReadings(){
		return minReadings;
	}
}
