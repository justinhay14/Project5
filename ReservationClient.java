import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ReservationClient implements Runnable{
    public static String choice = "";
    public static void main(String args[]) {
        Thread t = new Thread(new ReservationClient());
        t.start();
        try {
            t.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Socket socket;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
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
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid hostname and port.",
                    "Airport Manager", JOptionPane.ERROR_MESSAGE);


            System.exit(0);
        }
        ///////////////////
        //JOptionPanes
        ImageIcon icon = new ImageIcon("src/image.png");
        Object options[] = {"Exit", "Book a Flight"};
        if(JOptionPane.showOptionDialog(null, "Welcome", "Airport Manager",
                JOptionPane.OK_OPTION, 0, icon, options, null) != 1) {
            System.exit(0);
        }
        /////////////////////
        options = new Object[]{"Exit", "Yes I want to book a Flight"};
        if(JOptionPane.showOptionDialog(null, "Do you want to book a flight?",
                "Airport Manager", 0, JOptionPane.PLAIN_MESSAGE, null, options,
                null) != 1) {
            System.exit(0);
        }
        /////////////////
        //main frame
        JFrame frame = new JFrame("Airport Manager");
        frame.setLayout(new BorderLayout());
        frame.setSize(350,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Choose a flight from the drop down menu.");
        JComboBox<String> airline = new JComboBox<>();
        JTextArea description = new JTextArea();
        description.setEditable(false);
        airline.addItem("Alaska");
        airline.addItem("Delta");
        airline.addItem("Southwest");
        JPanel bottomPanel = new JPanel();
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
        ///////////////////
        //Small pop up panel (\)
        JFrame flightInfo = new JFrame("Passengers");
        flightInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        flightInfo.setLayout(new BorderLayout());
        flightInfo.setSize(200,200);
        JPanel info = new JPanel();
        JPanel buttons = new JPanel();
        JPanel top = new JPanel();
        JButton exitWin = new JButton("Exit");
        buttons.add(exitWin);
        JTextArea passengerInfo = new JTextArea();
        passengerInfo.setEditable(false);
        JLabel label1 = new JLabel();
        info.add(passengerInfo);
        top.add(label1);
        flightInfo.add(top, BorderLayout.NORTH);
        flightInfo.add(info, BorderLayout.CENTER);
        flightInfo.add(buttons, BorderLayout.SOUTH);
        /////////////////////
        //Network IO

        ////////////////////
        //Listeners for buttons
        exitWin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                flightInfo.setVisible(false);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        chooseFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                flightInfo.setVisible(false);
                chooseFlight.setVisible(false);
                airline.setVisible(false);
            }
        });
        ObjectOutputStream finalOut = out;
        ObjectInputStream finalIn = in;
        airline.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyChar() == '\\' && airline.getSelectedIndex() != -1) {
                    String label = "";
                    ArrayList<Passenger> passengers = new ArrayList<>();
                    if(airline.getSelectedIndex() == 0) {
                        label = "Alaska Airlines ";
                        try {
                            finalOut.writeObject("ACOUNT");
                            finalOut.flush();
                            label = label + finalIn.readObject().toString() + "/";
                            System.out.println(label);
                            finalOut.writeObject("ACAP");
                            finalOut.flush();
                            label = label + finalIn.readObject().toString();
                            System.out.println("received2");
                            finalOut.writeObject("APASS");
                            finalOut.flush();
                            passengers = (ArrayList<Passenger>) finalIn.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if(airline.getSelectedIndex() == 1) {
                        label = "Delta Airlines ";
                        try {
                            finalOut.writeObject("DCOUNT");
                            finalOut.flush();
                            label = label + finalIn.readObject().toString() + "/";
                            finalOut.writeObject("DCAP");
                            finalOut.flush();
                            label = label + finalIn.readObject().toString();
                            finalOut.writeObject("DPASS");
                            finalOut.flush();
                            passengers = (ArrayList<Passenger>) finalIn.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if(airline.getSelectedIndex() == 2) {
                        label = "Delta Airlines ";
                        try {
                            finalOut.writeObject("SCOUNT");
                            finalOut.flush();
                            label = label + finalIn.readObject().toString() + "/";
                            finalOut.writeObject("SCAP");
                            finalOut.flush();
                            label = label + finalIn.readObject().toString();
                            finalOut.writeObject("SPASS");
                            finalOut.flush();
                            passengers = (ArrayList<Passenger>) finalIn.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    String answer = "";
                    for(Passenger line : passengers) {
                        answer = answer + line.toString() + "\n";
                    }
                    passengerInfo.setText(answer);
                    label1.setText(label);
                    flightInfo.setVisible(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        airline.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                flightInfo.setVisible(false);
                if(itemEvent.getItem().toString().equals("Alaska")) {
                    choice = "Alaska";
                    description.setText("Alaska Airlines is one of the airlines we offer here at \nPurdue Airport.");
                    /////////////setText
                    //label1.setText("Alaska Airlines");
                    passengerInfo.setText("Mango");
                    /////////////
                } else if(itemEvent.getItem().toString().equals("Delta")) {
                    choice = "Delta";
                    description.setText("Delta is a premier airline at Purdue. You will love it.");
                    /////////////setText
                    //label1.setText("Delta Airlines");
                    passengerInfo.setText("Apple");
                    /////////////
                } else if(itemEvent.getItem().toString().equals("Southwest")) {
                    choice = "Southwest";
                    description.setText("Southwest is proud to offer flights to Purdue.");
                    /////////////setText
                    //label1.setText("Southwest Airlines");
                    passengerInfo.setText("Banana");
                    /////////////
                }
            }
        });
    }

    /*public static class ResponseListener implements Runnable {

        @Override
        public void run() {

        }
    }*/
}
