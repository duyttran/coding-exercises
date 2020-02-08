# Elevator coding sample

This code was written for a coding challenge, design an Elevator System that handles request from users to get an elevator and send an elevator to a given floor. The Elevator System should be able to step through time and show how the elevators are changing position. 

# Build

You can build my project using `gradle build eclipse`

# Running

You can run my half written tests `ElevatorControlSystemimpl`. I did not get to complete a full test suite yet and no running application that will loop for input yet. You will have to add to test and create code to instantiate `ElevatorControlSytsemImpl` and write more tests.

# Design

At the highest level, `ElevatorControlSystem` is the interface that will 
 - return the state of the system
 - accept request to send elevator to given floor
 - accept pickup request from floor and in given direction
 - step through system states to simulate elevators moving and requests being served

I have an Implementation class which simple stores all the elevators in the a HashMap. This allows us to access the elevators O(1) for changing the direction, moving the elevator, and updating the floor it is traveling too. I also have a queue for pending requests if a pickup request comes in and I cannot fulfill it immediately because all elevators are busy. The implementation also takes a AI class that figures out how to match elevators to request. Since this could be implemented different ways, this is a separate class that might be swapped in and out, or upgraded in the ElevatorControlSystem. 

# Accepting request to send elevator to given floor

This is currently done naiively, allowing user to override. I ran out of time, but in future cases, you could make sure they can only request floors in the given direction the elevator is already going.

# Accepting pickup requests

This calls out to the AI machine to find the best elevator to serve the request. If the request cannot be served, the pickup request will be put onto a queue.

# Finding the best pickup request

The Elevator System will find the best elevator by first finding all valid elevators. Valid elevators are in a HOLD state, as in not going UP or DOWN, basically finished serving its previous request and goal floor. Or a valid elevator can be an elevator that is going in the same direction as the pickup request and the pickup floor is betwen where the elevator is currently, and the goal floor. 

After we recover all the valid elevators, the elevator system does a sort by the distance from the pickup floor, to find the closest valid elevator to serve the request. This is a simple algorithm that will work since there is only 16 elevators.

# State cycle of Elevator System

For each step in the system, the elevator system will move all elevators in the correct direction of the goal floor, and assign pending pickup requests to available elevators. 
