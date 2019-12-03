import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
/**
 * @author Justin Hay, Justin Michaels
 * @version 12/2/2019
 */
public class ReservationClient {
    private static String choice = "";
    private static String boardingPass = "";
    private static String passInfo = "";

    public static void main(String[] args) {
        Thread t = new Thread(new ResponseListener());
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ResponseListener implements Runnable {
        public void run() {
            Socket socket;
            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            try {
                String ip = JOptionPane.showInputDialog(null,
                        "Type in the hostname of the Reservation Server.", "Airport Manager",
                        JOptionPane.QUESTION_MESSAGE);
                if (ip == null) {
                    return;
                }
                String port1 = JOptionPane.showInputDialog(null,
                        "Type in the port of the Reservation Server.", "Airport Manager",
                        JOptionPane.QUESTION_MESSAGE);
                if (port1 == null) {
                    return;
                }
                int port = Integer.parseInt(port1);
                socket = new Socket(ip, port);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid hostname and port.",
                        "Airport Manager", JOptionPane.ERROR_MESSAGE);


                return;
            }
            ///////////////////
            //JOptionPanes
            ImageIcon icon = new ImageIcon("src/image.png");
            Object[] options = {"Exit", "Book a Flight"};
            if (JOptionPane.showOptionDialog(null, "Welcome", "Airport Manager",
                    JOptionPane.OK_OPTION, 0, icon, options, null) != 1) {
                return;
            }
            /////////////////////
            options = new Object[]{"Exit", "Yes I want to book a Flight"};
            if (JOptionPane.showOptionDialog(null, "Do you want to book a flight?",
                    "Airport Manager", 0, JOptionPane.PLAIN_MESSAGE, null, options,
                    null) != 1) {
                return;
            }
            /////////////////
            //main frame
            JFrame frame = new JFrame("Airport Manager");
            frame.setLayout(new BorderLayout());
            frame.setSize(500, 300);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel panel = new JPanel();
            JLabel label = new JLabel("Choose a flight from the drop down menu.");
            JComboBox<String> airline = new JComboBox<>();
            JTextArea description = new JTextArea();
            JTextArea boardingPassArea = new JTextArea();
            description.setEditable(false);
            boardingPassArea.setEditable(false);
            airline.addItem("Alaska");
            airline.addItem("Delta");
            airline.addItem("Southwest");
            JPanel bottomPanel = new JPanel();
            JButton exit = new JButton("EXIT");
            JButton chooseFlight = new JButton("CHOOSE THIS FLIGHT");
            panel.add(label);
            panel.add(airline);
            panel.add(description);
            panel.add(boardingPassArea);
            JButton decline = new JButton("No I want a different flight");
            JButton accept = new JButton("Yes I want this flight");
            JButton refresh = new JButton("Refresh");
            bottomPanel.add(exit);
            bottomPanel.add(chooseFlight);
            bottomPanel.add(decline);
            bottomPanel.add(accept);
            bottomPanel.add(refresh);
            decline.setVisible(false);
            accept.setVisible(false);
            refresh.setVisible(false);
            //
            //JPanel center = new JPanel();
            JLabel firstNameLabel = new JLabel("Enter your first name:");
            JTextField firstNameBox = new JTextField(30);
            JLabel lastNameLabel = new JLabel("Enter your last name:");
            JTextField lastNameBox = new JTextField(30);
            JLabel ageLabel = new JLabel("Enter your age:");
            JTextField ageBox = new JTextField(30);
            panel.add(firstNameLabel);
            panel.add(firstNameBox);
            panel.add(lastNameLabel);
            panel.add(lastNameBox);
            panel.add(ageLabel);
            panel.add(ageBox);
            firstNameLabel.setVisible(false);
            firstNameBox.setVisible(false);
            lastNameLabel.setVisible(false);
            lastNameBox.setVisible(false);
            ageLabel.setVisible(false);
            ageBox.setVisible(false);
            //center.setVisible(false);
            //
            //panel.add(center);
            JButton next = new JButton("Next");
            next.setVisible(false);
            bottomPanel.add(next);
            frame.add(panel);
            frame.add(bottomPanel, BorderLayout.SOUTH);
            frame.setVisible(true);
            airline.setSelectedIndex(-1);
            ///////////////////
            //Small pop up panel (\)
            JFrame flightInfo = new JFrame("Passengers");
            flightInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            flightInfo.setLayout(new BorderLayout());
            flightInfo.setSize(200, 700);
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
            //Listeners for buttons
            ObjectOutputStream finalOut = out;
            ObjectInputStream finalIn = in;
            exitWin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    flightInfo.setVisible(false);
                }
            });
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JOptionPane.showMessageDialog(null,
                            "Thank you for using Airport Manager.", "Airport Manager",
                            JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
            });
            chooseFlight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    int taken = 0;
                    int spots = 0;
                    try {
                        if (choice.equals("Alaska")) {
                            finalOut.writeObject("ACOUNT");
                            finalOut.flush();
                            taken = Integer.parseInt(finalIn.readObject().toString());
                            finalOut.writeObject("ACAP");
                            finalOut.flush();
                            spots = Integer.parseInt(finalIn.readObject().toString());
                        } else if (choice.equals("Delta")) {
                            finalOut.writeObject("DCOUNT");
                            finalOut.flush();
                            taken = Integer.parseInt(finalIn.readObject().toString());
                            finalOut.writeObject("DCAP");
                            finalOut.flush();
                            spots = Integer.parseInt(finalIn.readObject().toString());
                        } else if (choice.equals("Southwest")) {
                            finalOut.writeObject("SCOUNT");
                            finalOut.flush();
                            taken = Integer.parseInt(finalIn.readObject().toString());
                            finalOut.writeObject("SCAP");
                            finalOut.flush();
                            spots = Integer.parseInt(finalIn.readObject().toString());
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (taken < spots) {
                        label.setText("Are you sure you want to book a flight on " + choice + " Airlines?");
                        accept.setVisible(true);
                        decline.setVisible(true);
                        description.setVisible(false);
                        boardingPassArea.setVisible(false);
                        flightInfo.setVisible(false);
                        chooseFlight.setVisible(false);
                        airline.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Please select a flight that is not full.", "Airport Manager",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            decline.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    label.setText("Choose a flight from the drop down menu.");
                    accept.setVisible(false);
                    decline.setVisible(false);
                    description.setVisible(true);
                    chooseFlight.setVisible(true);
                    airline.setVisible(true);
                }
            });
            accept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    label.setText("-----------------------Please input the following information---------" +
                            "----------------");
                    firstNameLabel.setVisible(true);
                    firstNameBox.setVisible(true);
                    lastNameLabel.setVisible(true);
                    lastNameBox.setVisible(true);
                    ageLabel.setVisible(true);
                    ageBox.setVisible(true);
                    decline.setVisible(false);
                    accept.setVisible(false);
                    next.setVisible(true);
                }
            });
            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String firstName = firstNameBox.getText();
                    String lastName = lastNameBox.getText();
                    String fraction = "";
                    try {
                        int age = Integer.parseInt(ageBox.getText());
                        Passenger passenger = new Passenger(firstName.toUpperCase(), lastName.toUpperCase(), age);
                        int confirmed = JOptionPane.showConfirmDialog(null,
                                "Please verify the following information\n" +
                                        "first name: " + firstName + "\nlast name: " + lastName + "\nage: " + age,
                                "Airport Manager", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (confirmed == 0) {
                            ArrayList<Passenger> passengers = new ArrayList<>();
                            if (choice.equals("Alaska")) {
                                finalOut.writeObject("APASS");
                                finalOut.flush();
                                passengers = (ArrayList<Passenger>) finalIn.readObject();
                                finalOut.writeObject("ACAP");
                                finalOut.flush();
                                fraction = finalIn.readObject().toString();
                            } else if (choice.equals("Delta")) {
                                finalOut.writeObject("DPASS");
                                finalOut.flush();
                                passengers = (ArrayList<Passenger>) finalIn.readObject();
                                finalOut.writeObject("DCAP");
                                finalOut.flush();
                                fraction = finalIn.readObject().toString();
                            } else if (choice.equals("Southwest")) {
                                finalOut.writeObject("SPASS");
                                finalOut.flush();
                                passengers = (ArrayList<Passenger>) finalIn.readObject();
                                finalOut.writeObject("SCAP");
                                finalOut.flush();
                                fraction = finalIn.readObject().toString();
                            }
                            String answer = "";
                            for (Passenger line : passengers) {
                                answer = answer + line.toString() + "\n";
                            }
                            finalOut.writeObject(null);
                            finalOut.writeObject(choice);
                            finalOut.writeObject(passenger);
                            //answer += passenger.toString() + "\n";
                            passInfo = passenger.toString();
                            boardingPass = finalIn.readObject().toString();
                            //System.out.println(passInfo + "\n" + boardingPass);

                            passengers = (ArrayList<Passenger>) finalIn.readObject();
                            answer = "";
                            for (Passenger line : passengers) {
                                answer = answer + line.toString() + "\n";
                            }
                            label.setText("Flight Data displaying for " + choice + " Airlines " +
                                    "which is now boarding at gate: " + boardingPass.substring(
                                    boardingPass.indexOf("gate ") + 5,
                                    boardingPass.lastIndexOf("---------------------------------------" +
                                            "------------------------------------------------------------" +
                                            "---------------------")));
                            label.setSize(100, 100);
                            description.setText(passengers.size() + "/" + fraction + "\n" + answer);
                            description.setVisible(true);
                            boardingPassArea.setText(boardingPass);
                            boardingPassArea.setVisible(true);
                            firstNameLabel.setVisible(false);
                            firstNameBox.setVisible(false);
                            lastNameLabel.setVisible(false);
                            lastNameBox.setVisible(false);
                            ageLabel.setVisible(false);
                            ageBox.setVisible(false);
                            next.setVisible(false);
                            refresh.setVisible(true);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid age",
                                "Airport Manager", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid name",
                                "Airport Manager", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            refresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        String fraction = "";
                        ArrayList<Passenger> passengers = new ArrayList<>();
                        if (choice.equals("Alaska")) {
                            finalOut.writeObject("APASS");
                            finalOut.flush();
                            passengers = (ArrayList<Passenger>) finalIn.readObject();
                            finalOut.writeObject("ACAP");
                            finalOut.flush();
                            fraction = finalIn.readObject().toString();
                        } else if (choice.equals("Delta")) {
                            finalOut.writeObject("DPASS");
                            finalOut.flush();
                            passengers = (ArrayList<Passenger>) finalIn.readObject();
                            finalOut.writeObject("DCAP");
                            finalOut.flush();
                            fraction = finalIn.readObject().toString();
                        } else if (choice.equals("Southwest")) {
                            finalOut.writeObject("SPASS");
                            finalOut.flush();
                            passengers = (ArrayList<Passenger>) finalIn.readObject();
                            finalOut.writeObject("SCAP");
                            finalOut.flush();
                            fraction = finalIn.readObject().toString();
                        }
                        String answer = "";
                        for (Passenger line : passengers) {
                            answer = answer + line.toString() + "\n";
                        }
                        description.setText(passengers.size() + "/" + fraction + "\n" + answer);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            airline.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {

                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {
                    if (keyEvent.getKeyChar() == '\\' && airline.getSelectedIndex() != -1) {
                        String label = "";
                        ArrayList<Passenger> passengers = new ArrayList<>();
                        if (airline.getSelectedIndex() == 0) {
                            label = "Alaska Airlines ";
                            try {
                                finalOut.writeObject("ACOUNT");
                                finalOut.flush();
                                label = label + finalIn.readObject().toString() + "/";
                                finalOut.writeObject("ACAP");
                                finalOut.flush();
                                label = label + finalIn.readObject().toString();
                                finalOut.writeObject("APASS");
                                finalOut.flush();
                                passengers = (ArrayList<Passenger>) finalIn.readObject();
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else if (airline.getSelectedIndex() == 1) {
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
                        } else if (airline.getSelectedIndex() == 2) {
                            label = "Southwest Airlines ";
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
                        for (Passenger line : passengers) {
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
                    if (itemEvent.getItem().toString().equals("Alaska")) {
                        choice = "Alaska";
                        description.setText("Alaska Airlines is one of the airlines we offer here" +
                                " at \nPurdue Airport.");
                        /////////////setText
                        //label1.setText("Alaska Airlines");
                        //passengerInfo.setText("Mango");
                        /////////////
                    } else if (itemEvent.getItem().toString().equals("Delta")) {
                        choice = "Delta";
                        description.setText("Delta is a premier airline at Purdue. You will love it.");
                        /////////////setText
                        //label1.setText("Delta Airlines");
                        //passengerInfo.setText("Apple");
                        /////////////
                    } else if (itemEvent.getItem().toString().equals("Southwest")) {
                        choice = "Southwest";
                        description.setText("Southwest is proud to offer flights to Purdue.");
                        /////////////setText
                        //label1.setText("Southwest Airlines");
                        //passengerInfo.setText("Banana");
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
}
