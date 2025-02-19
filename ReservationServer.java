import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
/**
 * @author Justin Hay, Justin Michaels
 * @version 12/2/2019
 */
public class ReservationServer {
    public static void main(String[] args) throws IOException {
        Random r = new Random();
        int port = 10000;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = null;
        System.out.println("Hosting Reservation Server on port: " + port);
        while (true) {
            try {
                socket = serverSocket.accept();
                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @author Justin Hay, Justin Michaels
     * @version 12/2/2019
     */
    public static class ClientHandler implements Runnable {
        private Socket socket;
        private Alaska alaska;
        private Delta delta;
        private Southwest sw;
        ArrayList<String> reservations;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public ArrayList<String> getAirlinesOpen() {
            try {
                reservations = new ArrayList<>();
                //read from current reservations.txt and put contents into an arraylist
                BufferedReader bfr = new BufferedReader(new FileReader(new File("reservations.txt")));
                String nextLine;
                while ((nextLine = bfr.readLine()) != null) {
                    reservations.add(nextLine);
                }
                bfr.close();

                //arraylist shows which airlines have open seats
                ArrayList<String> openAirlines = new ArrayList<String>();

                //create new alaska airline from reservations.txt
                int currentAlaskaPassengers = 0;
                for (int i = reservations.indexOf("Alaska passenger list") + 1; i < reservations.indexOf("DELTA") - 1;
                     i += 2) {
                    currentAlaskaPassengers++;
                }
                System.out.println(currentAlaskaPassengers);
                alaska = new Alaska(Integer.parseInt(reservations.get(reservations.indexOf("ALASKA") + 1).substring(
                        reservations.get(reservations.indexOf("ALASKA") + 1).indexOf("/") + 1)),
                        currentAlaskaPassengers);
                for (int i = reservations.indexOf("Alaska passenger list") + 1;
                     i < reservations.indexOf("Alaska passenger list") + 1 + alaska.getSpotsFilled() * 2; i += 2) {
                    if (reservations.get(i).length() == 0)
                        break;
                    alaska.addPassenger(new Passenger(reservations.get(i).substring(0, 1),
                            reservations.get(i).substring(reservations.get(i).indexOf(" ") + 1,
                                    reservations.get(i).indexOf(",")),
                            Integer.parseInt(reservations.get(i).substring(reservations.get(i).lastIndexOf(
                                    " ") + 1))));
                }
                alaska.updateSpotsFilled();
                if (alaska.getSpotsFilled() < alaska.getCapacity()) {
                    openAirlines.add(alaska.getAirlineName());
                }
                //create new delta airline from reservations.txt
                int currentDeltaPassengers = 0;
                for (int i = reservations.indexOf("Delta passenger list") + 1; i < reservations.indexOf("SOUTHWEST")
                        - 1;
                     i += 2) {
                    currentDeltaPassengers++;
                }
                delta = new Delta(Integer.parseInt(reservations.get(reservations.indexOf("DELTA") + 1).substring(
                        reservations.get(reservations.indexOf("DELTA") + 1).indexOf("/") + 1)),
                        currentDeltaPassengers);
                for (int i = reservations.indexOf("Delta passenger list") + 1;
                     i < reservations.indexOf("Delta passenger list") + 1 + delta.getSpotsFilled() * 2; i += 2) {
                    if (reservations.get(i).length() == 0)
                        break;
                    delta.addPassenger(new Passenger(reservations.get(i).substring(0, 1),
                            reservations.get(i).substring(reservations.get(i).indexOf(" ") + 1,
                                    reservations.get(i).indexOf(",")),
                            Integer.parseInt(reservations.get(i).substring(reservations.get(i).lastIndexOf(
                                    " ") + 1))));
                }
                delta.updateSpotsFilled();
                if (delta.getSpotsFilled() < delta.getCapacity()) {
                    openAirlines.add(delta.getAirlineName());
                }

                //create new southwest airline from reservations.txt
                int currentSouthwestPassengers = 0;
                for (int i = reservations.indexOf("Southwest passenger list") + 1; i < reservations.size();
                     i += 2) {
                    currentSouthwestPassengers++;
                }
                sw = new Southwest(Integer.parseInt(reservations.get(reservations.indexOf("SOUTHWEST") + 1).substring(
                        reservations.get(reservations.indexOf("SOUTHWEST") + 1).indexOf("/") + 1)),
                        currentSouthwestPassengers);
                for (int i = reservations.indexOf("Southwest passenger list") + 1;
                     i < reservations.indexOf("Southwest passenger list") + 1 + sw.getSpotsFilled() * 2; i += 2) {
                    if (reservations.get(i).length() == 0)
                        break;
                    sw.addPassenger(new Passenger(reservations.get(i).substring(0, 1),
                            reservations.get(i).substring(reservations.get(i).indexOf(" ") + 1,
                                    reservations.get(i).indexOf(",")),
                            Integer.parseInt(reservations.get(i).substring(reservations.get(i).lastIndexOf(
                                    " ") + 1))));
                }
                sw.updateSpotsFilled();
                if (sw.getSpotsFilled() < sw.getCapacity()) {
                    openAirlines.add(sw.getAirlineName());
                }
                return openAirlines;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void run() {
            try {
                getAirlinesOpen();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                //send list of open airlines to client
                String[] openAirlinesArray = {"Alaska", "Delta", "Southwest"};
                //out.writeObject(openAirlinesArray);
                //out.flush();

                String nextObj;

                //send client back requested data about a flight
                while ((nextObj = (String) in.readObject()) != null) {
                    if (nextObj.equals("APASS")) { //send back passenger list of alaska
                        out.writeObject(alaska.getPassengers());
                    } else if (nextObj.equals("ACOUNT")) { //send back current number of passengers of alaska
                        System.out.println(alaska.getSpotsFilled() + "/");
                        out.writeObject(alaska.getSpotsFilled());
                    } else if (nextObj.equals("ACAP")) { //send back plane capacity of alaska
                        out.writeObject(alaska.getCapacity());
                    } else if (nextObj.equals("DPASS")) { //send back passenger list of delta
                        out.writeObject(delta.getPassengers());
                    } else if (nextObj.equals("DCOUNT")) { //send back current number of passengers of delta
                        out.writeObject(delta.getSpotsFilled());
                    } else if (nextObj.equals("DCAP")) { //send back plane capacity of delta
                        out.writeObject(delta.getCapacity());
                    } else if (nextObj.equals("SPASS")) { //send back passenger list of southwest
                        out.writeObject(sw.getPassengers());
                    } else if (nextObj.equals("SCOUNT")) { //send back current number of passengers of southwest
                        out.writeObject(sw.getSpotsFilled());
                    } else if (nextObj.equals("SCAP")) { //send back plane capacity of southwest
                        out.writeObject(sw.getCapacity());
                    }
                    out.flush();
                }

                //read selected airline and a passenger from the client
                String airline = (String) in.readObject();
                Passenger passenger = (Passenger) in.readObject();

                //recheck if airline is full and update
                /*while (!getAirlinesOpen().contains(airline)) {
                    openAirlinesArray = (String[]) getAirlinesOpen().toArray();
                    out.writeObject(openAirlinesArray);
                    out.flush();
                    airline = (String) in.readObject();
                    passenger = (Passenger) in.readObject();
                }*/

                //create gate and boarding pass for passenger and send back to client
                char terminalChar = ' ';
                if (airline.toUpperCase().equals("ALASKA")) {
                    terminalChar = 'A';
                } else if (airline.toUpperCase().equals("DELTA")) {
                    terminalChar = 'B';
                } else if (airline.toUpperCase().equals("SOUTHWEST")) {
                    terminalChar = 'C';
                }
                passenger.setBoardingPass(new BoardingPass(passenger, airline, new Gate(terminalChar)));
                passenger.setFirstName(passenger.getFirstName().toUpperCase());
                passenger.setLastName(passenger.getLastName().toUpperCase());
                out.writeObject(passenger.getBoardingPass());
                out.flush();

                //add passenger information to reservations.txt
                if (airline.toUpperCase().equals("ALASKA")) {
                    alaska.addPassenger(passenger);
                } else if (airline.toUpperCase().equals("DELTA")) {
                    delta.addPassenger(passenger);
                } else if (airline.toUpperCase().equals("SOUTHWEST")) {
                    sw.addPassenger(passenger);
                }


                /*reservations.add(reservations.lastIndexOf("---------------------" +
                        airline.toUpperCase()) + 1, passenger.toString());
                reservations.add(reservations.indexOf(passenger.toString()) + 1,
                        "---------------------" + airline.toUpperCase());*/

                PrintWriter pw = new PrintWriter("reservations.txt");
                pw.write(alaska.toString() + "\n\n" + delta.toString() + "\n\n" + sw.toString() + "\n\nEOF");
                pw.flush();
                pw.close();
                getAirlinesOpen();
                if (airline.toUpperCase().equals("ALASKA")) {
                    out.writeObject(alaska.getPassengers());
                    out.flush();
                } else if (airline.toUpperCase().equals("DELTA")) {
                    out.writeObject(delta.getPassengers());
                    out.flush();
                } else if (airline.toUpperCase().equals("SOUTHWEST")) {
                    out.writeObject(sw.getPassengers());
                    out.flush();
                }

                String nextRequest = "";
                while (true) {
                    nextRequest = (String) in.readObject();
                    getAirlinesOpen();
                    if (nextRequest.equals("APASS")) { //send back passenger list of alaska
                        out.writeObject(alaska.getPassengers());
                    } else if (nextRequest.equals("DPASS")) { //send back passenger list of delta
                        out.writeObject(delta.getPassengers());
                    } else if (nextRequest.equals("SPASS")) { //send back passenger list of southwest
                        out.writeObject(sw.getPassengers());
                    } else if (nextRequest.equals("ACAP")) {
                        out.writeObject(alaska.getCapacity());
                    } else if (nextRequest.equals("DCAP")) {
                        out.writeObject(delta.getCapacity());
                    } else if (nextRequest.equals("SCAP")) {
                        out.writeObject(sw.getCapacity());
                    }
                    out.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}