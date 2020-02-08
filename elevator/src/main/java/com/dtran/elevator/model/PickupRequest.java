package com.dtran.elevator.model;

import com.dtran.elevator.model.Elevator.Direction;

/**
 * PickupRequest contains the request made by a user to retrieve an elevator
 */
public class PickupRequest {
	
	private int floorNumber;
	private Direction direction;
	public PickupRequest(int floorNumber, Direction direction) {
		this.floorNumber = floorNumber;
		this.direction = direction;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	@Override
	public String toString() {
		return "PickupRequest [floorNumber=" + floorNumber + ", direction=" + direction + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + floorNumber;
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
		PickupRequest other = (PickupRequest) obj;
		if (direction != other.direction)
			return false;
		if (floorNumber != other.floorNumber)
			return false;
		return true;
	}
	
	
}
