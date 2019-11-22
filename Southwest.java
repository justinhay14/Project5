import java.util.ArrayList;

public class Southwest implements Airline {
    private int capacity;
    private int spotsFilled;
    private ArrayList<Passenger> passengers;
    public Southwest(int capacity, int spotsFilled) {
        this.capacity = capacity;
        this.spotsFilled = spotsFilled;
    }
    @Override
    public String getAirlineName() {
        return "Southwest";
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSpotsFilled() {
        return spotsFilled;
    }

    @Override
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }
    public String toString() {
        String a = "SOUTHWEST\n" + spotsFilled + "/" + capacity + "\nSouthwest Passenger List";
        for(int i = 0; i < passengers.size(); i++) {
            a = a + "\n" + passengers.get(i).toString() + "\n---------------------SOUTHWEST";
        }
        return a;
    }
}
