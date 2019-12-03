import java.io.Serializable;
import java.util.Random;

public class Gate implements Serializable {
    private char terminal;
    private int gate;

    public Gate(char terminal) {
        this.terminal = terminal;
        Random r = new Random();
        gate = r.nextInt(18) + 1;
    }

    public char getTerminal() {
        return terminal;
    }

    public void setTerminal(char terminal) {
        this.terminal = terminal;
    }

    public int getGate() {
        return gate;
    }

    public void setGate(int gate) {
        this.gate = gate;
    }

    public String toString() {
        return "" + terminal + gate;
    }

    public boolean equals(Object o) {
        return o.toString().equals(toString());
    }
}
