package com.dtran.elevator.model;

/**
 * Elevator contains all the necessary information about where the elevator is, where it needs to go, and in what direction.
 */
public class Elevator {
	
	public enum Direction {
		UP, DOWN, HOLD
	}
	
	private int elevatorId;
	private Direction direction;
	private int floorNumber;
	private int goalFloorNumber;
	
	public Elevator(int elevatorId) {
		this.elevatorId = elevatorId;
		this.direction = Direction.HOLD;
		this.floorNumber = 0;
		this.goalFloorNumber = 0;
	}

	public int getElevatorId() {
		return elevatorId;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public int getGoalFloorNumber() {
		return goalFloorNumber;
	}

	public void setGoalFloorNumber(int goalFloorNumber) {
		this.goalFloorNumber = goalFloorNumber;
	}

	@Override
	public String toString() {
		return "Elevator [elevatorId=" + elevatorId + ", direction=" + direction + ", floorNumber=" + floorNumber
				+ ", goalFloorNumber=" + goalFloorNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + elevatorId;
		result = prime * result + floorNumber;
		result = prime * result + goalFloorNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elevator other = (Elevator) obj;
		if (direction != other.direction)
			return false;
		if (elevatorId != other.elevatorId)
			return false;
		if (floorNumber != other.floorNumber)
			return false;
		if (goalFloorNumber != other.goalFloorNumber)
			return false;
		return true;
	}
	
	
}
