import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReservationClient {
    public static void main(String args[]) {
        /*Socket socket;
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
        }*/
        ImageIcon icon = new ImageIcon("src/image.png");
        JOptionPane.showMessageDialog(null, "Welcome", "Airport Manager",
                JOptionPane.INFORMATION_MESSAGE, icon);
        JFrame frame = new JFrame("Airport Manager");
        frame.setLayout(new BorderLayout());
        frame.setSize(350,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Choose a flight from the drop down menu.");
        JComboBox<String> airline = new JComboBox<>();
        JTextArea description = new JTextArea();
        airline.addItem("Alaska");
        airline.addItem("Delta");
        airline.addItem("Southwest");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exit = new JButton("EXIT");
        JButton chooseFlight = new JButton("CHOOSE THIS FLIGHT");
        panel.add(label);
        panel.add(airline);
        panel.add(description);
        bottomPanel.add(exit);
        bottomPanel.add(chooseFlight);
        frame.add(panel);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        airline.setSelectedIndex(-1);
        while(true) {
            int selection = airline.getSelectedIndex();
            if(selection == 0) {
                description.setText("Alaska----------------------------------------------------------------------------------------------------------------------------------");
            } else if(selection == 1) {
                description.setText("Delta");
            } else if(selection == 2) {
                description.setText("Southwest");
            }
        }
    }
}
