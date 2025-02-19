import java.io.Serializable;
/**
 * @author Justin Hay, Justin Michaels
 * @version 12/2/2019
 */
public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private int age;
    private BoardingPass boardingPass;

    public Passenger(String firstName, String lastName, int age) throws IllegalArgumentException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        if (badName(firstName) || badName(lastName) || age < 0)
            throw new IllegalArgumentException();
    }

    private boolean badName(String name) {
        boolean answer = false;
        for (int i = 0; i < name.length(); i++) {
            if ((((int) name.toUpperCase().charAt(i)) < 65 || ((int) name.toUpperCase().charAt(i)) > 90) &&
                    ((int) name.toUpperCase().charAt(i)) != 45)
                answer = true;
        }
        return answer;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return firstName.substring(0, 1) + ". " + lastName + ", " + age;
    }

    public boolean equals(Object o) {
        return ((Passenger) o).getFirstName().equals(firstName) && ((Passenger) o).getLastName().equals(lastName) &&
                ((Passenger) o).getAge() == age;
    }

    public void setBoardingPass(BoardingPass boardingPass) {
        this.boardingPass = boardingPass;
    }

    public BoardingPass getBoardingPass() {
        return boardingPass;
    }
}
