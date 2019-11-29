import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReservationClient {
    public static void main(String args[]) {
        Socket socket;
        try {
            String ip = JOptionPane.showInputDialog(null,
                    "Type in the hostname of the Reservation Server.", "Airport Manager",
                    JOptionPane.QUESTION_MESSAGE);
            if(ip == null) {
                System.exit(0);
            }
            String port1 = JOptionPane.showInputDialog(null,
                    "Type in the port of the Reservation Server.", "Airport Manager",
                    JOptionPane.QUESTION_MESSAGE);
            if(port1 == null) {
                System.exit(0);
            }
            int port = Integer.parseInt(port1);
            socket = new Socket(ip, port);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid hostname and port.",
                    "Airport Manager", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        ImageIcon icon = new ImageIcon("src/image.png");
        JOptionPane.showMessageDialog(null, "Welcome", "Airport Manager",
                JOptionPane.INFORMATION_MESSAGE, icon);
        JFrame frame = new JFrame("Airport Manager");
        frame.setSize(1280,720);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        JComboBox<String> airline = new JComboBox<>();
        panel.add(airline);
        frame.setVisible(true);
    }
}
