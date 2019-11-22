import java.io.Serializable;

public class BoardingPass implements Serializable {
    private String airline;
    private Passenger passenger;
    private Gate gate;
    public BoardingPass(Passenger passenger, String airline, Gate gate) {
        this.passenger = passenger;
        this.airline = airline;
        this.gate = gate;
    }
    public String toString() {
        return airline + ": " + passenger.getFirstName() + " " + passenger.getLastName() + ", " + passenger.getAge() +
                " Gate: " + gate.toString();
    }
}
