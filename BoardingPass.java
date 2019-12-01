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
        return "----------------------------------------\n" + "BOARDING PASS FOR FLIGHT 18000 WITH " + airline +
                " AIRLINES\n" + "PASSENGER FIRST NAME: " + passenger.getFirstName() +
                "\nPASSENGER LAST NAME: " + passenger.getLastName() + "\nPASSENGER AGE: " + passenger.getAge() +
                "\nYou can now begin boarding at gate " + gate.toString() +
                "\n----------------------------------------";
    }
}
