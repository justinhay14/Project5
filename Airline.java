import java.io.Serializable;
import java.util.ArrayList;

public interface Airline extends Serializable {
    String getAirlineName();
    int getCapacity();
    int getSpotsFilled();
    ArrayList<Passenger> getPassengers();
    String toString();
    void addPassenger(Passenger passenger);
}
