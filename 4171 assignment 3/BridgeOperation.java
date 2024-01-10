/*
    * Name: Abdulrahman AL Kindi
    * Banner ID: B00858783
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BridgeOperation {
    public static void main (String []arg) throws FileNotFoundException {

        // creating the database by using the method to read bridge database text file
        ArrayList<HostPortPair> bridgeDatabase= readFDB();
        PrintWriter output= new PrintWriter("BridgeOutput.txt");
        System.out.print("Insert frames text file name: ");
        Scanner kb = new Scanner(System.in);
        String filename = kb.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);

        while (inputFile.hasNextLine()){
            String source= inputFile.nextLine();
            String dest= inputFile.nextLine();
            char port= inputFile.nextLine().charAt(0);
            // getting the index of source in the database. if it doesn't exist index is -1
            int index=  checkSourceInFDB(new HostPortPair(source,port),bridgeDatabase);

            if (index!=-1){
                // if the port number attached to mac address in FDB not the same as the new one
                if (port!=bridgeDatabase.get(index).getPortNum()){
                    // changing the port num to the newest one
                    bridgeDatabase.get(index).setPortNum(port);
                }
            }
            else{
                // creating a new record for the node and adding it to database
                HostPortPair newest= new HostPortPair(source,port);
                bridgeDatabase.add(newest);

            }

            String decision;
            // checking if destination in FDB
            index= checkSourceInFDB(new HostPortPair(dest,port),bridgeDatabase);
            if (index!=-1){
                if (port!=bridgeDatabase.get(index).getPortNum()){
                    decision= "Forwarded on port "+ bridgeDatabase.get(index).getPortNum();
                }
                else{
                    decision="Discard";
                }

            }
            else{
                decision="Broadcast on all ports except "+ port;
            }

            printOutput(source,dest,port,decision,output);
        }
        output.close();
        PrintWriter databaseTxt= new PrintWriter("BridgeFDB.txt");
        for (HostPortPair hostPortPair: bridgeDatabase){
            databaseTxt.println(hostPortPair.getHostAddress());
            databaseTxt.println(hostPortPair.getPortNum());
        }
        databaseTxt.close();


    }

    /*
        * @Description: a method used to read the database text file and create an arraylist with the information
        * @throws: a file not found exception in case the file is not found
        * @returns an arraylist list containing object pairs with each destination and its relative port
     */
    public static ArrayList<HostPortPair> readFDB() throws FileNotFoundException {
        ArrayList<HostPortPair> bridgeDatabase= new ArrayList<>();
        Scanner kb = new Scanner(System.in);
        System.out.print("Insert database text file name: ");
        String filename = kb.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);

        while (inputFile.hasNextLine()){
            bridgeDatabase.add(new HostPortPair(inputFile.nextLine(),inputFile.nextLine().charAt(0)));
        }
        return bridgeDatabase;
    }

    /*
        @Description: a method used to check if the a mac address is already on the FDB
        @returns index returns the index of the mac address on the arraylist or -1 in the case the mac address doesn't exist on the database
     */
    public static int checkSourceInFDB(HostPortPair source, ArrayList<HostPortPair> database){

         int index=-1;
         int counter=0;
        for (HostPortPair hostPortPair : database) {
            if (hostPortPair.getHostAddress().equals(source.getHostAddress())) {
                index=counter;
                break;
            }
            counter++;
        }

        return index;
    }

    // prints output
    public static void printOutput (String src, String dst, char port, String decision,PrintWriter output){

        output.printf("%20s %20s %5c %s\n",src,dst,port,decision);
    }


}
