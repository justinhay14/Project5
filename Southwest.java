import java.util.ArrayList;
/**
 * @author Justin Hay, Justin Michaels
 * @version 12/2/2019
 */
public class Southwest implements Airline {
    private int capacity;
    private int spotsFilled;
    private ArrayList<Passenger> passengers;

    public Southwest(int capacity, int spotsFilled) {
        passengers = new ArrayList<>();
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
        spotsFilled++;
        passengers.add(passenger);
        //updateSpotsFilled();
    }

    @Override
    public void updateSpotsFilled() {
        spotsFilled = passengers.size();
    }

    public String toString() {
        String a = "SOUTHWEST\n" + spotsFilled + "/" + capacity + "\nSouthwest passenger list";
        for (int i = 0; i < passengers.size(); i++) {
            a = a + "\n" + passengers.get(i).toString() + "\n---------------------SOUTHWEST";
        }
        return a;
    }
}
