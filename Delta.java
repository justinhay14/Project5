import java.util.ArrayList;

public class Delta implements Airline {
    private int capacity;
    private int spotsFilled;
    private ArrayList<Passenger> passengers;

    public Delta(int capacity, int spotsFilled) {
        passengers = new ArrayList<>();
        this.capacity = capacity;
        this.spotsFilled = spotsFilled;
    }
    @Override
    public String getAirlineName() {
        return "Delta";
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
        spotsFilled++;
        passengers.add(passenger);
        //updateSpotsFilled();
    }

    @Override
    public void updateSpotsFilled() {
        spotsFilled = passengers.size();
    }

    public String toString() {
        String a = "DELTA\n" + spotsFilled + "/" + capacity + "\nDelta passenger list";
        for(int i = 0; i < passengers.size(); i++) {
            a = a + "\n" + passengers.get(i).toString() + "\n---------------------DELTA";
        }
        return a;
    }
}
