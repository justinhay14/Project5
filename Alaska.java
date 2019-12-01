import java.util.ArrayList;

public class Alaska implements Airline {
    private int capacity;
    private int spotsFilled;
    private ArrayList<Passenger> passengers;

    public Alaska(int capacity, int spotsFilled) {
        passengers = new ArrayList<>();
        this.capacity = capacity;
        this.spotsFilled = spotsFilled;
    }
    @Override
    public String getAirlineName() {
        return "Alaska";
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
    }

    public String toString() {
        String a = "ALASKA\n" + spotsFilled + "/" + capacity + "\nAlaska Passenger List";
        for(int i = 0; i < passengers.size(); i++) {
            a = a + "\n" + passengers.get(i).toString() + "\n---------------------ALASKA";
        }
        return a;
    }
}
