import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author Justin Hay, Justin Michaels
 * @version 12/2/2019
 */
public interface Airline extends Serializable {
    String getAirlineName();
    int getCapacity();
    int getSpotsFilled();
    ArrayList<Passenger> getPassengers();
    String toString();
    void addPassenger(Passenger passenger);
    void updateSpotsFilled();
}
