/*
    * Name: Abdulrahman Al Kindi
    * Banner ID: B00858783
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class demo {


    public static void main (String []arg) throws FileNotFoundException {

        File file = new File("RoutingTable.txt");
        Scanner inputFile = new Scanner(file);
        // creating routing table
        ArrayList<RoutingTableItem> routingTable= new ArrayList<>();

        RoutingTableItem genericIp=new RoutingTableItem("","","",0);

        while (inputFile.hasNextLine()){
            // getting the ip address and the mask separated
            String [] ip= inputFile.nextLine().split("/");
            if (Integer.parseInt(ip[1])<=24){
                ip[0]=getClassOfIp(ip[0]);
            }
            routingTable.add(new RoutingTableItem(ip[0],inputFile.nextLine(),inputFile.nextLine(),Integer.parseInt(ip[1])));
            // saving the generic routing table item
            if (ip[1].equals("0")){
                genericIp= routingTable.get(routingTable.size()-1);
            }


        }
        System.out.println("");

        File file1= new File("RandomPackets.txt");
        inputFile.close();
        inputFile= new Scanner(file1);

        int index=-1;

        PrintWriter output = new PrintWriter("RoutingOutput.txt");
        // looping through all ip addresses
        while (inputFile.hasNextLine()){
            String ip = inputFile.nextLine();
            for (int i=0; i<routingTable.size();i++){
                // checking if the ip is host specific
                if (ip.equals(routingTable.get(i).getIp())){
                   index=i;
                }
            }

            String changedIp;
            if (index==-1){
                // changing ip to 255.255.255.0
                changedIp=  getClassOfIp(ip);
                for (int i=0; i<routingTable.size();i++){
                    // checking if the ip passed is network specific
                    if (changedIp.equals(routingTable.get(i).getIp()) && routingTable.get(i).getMask()<=24){
                        index=i;
                    }
                }
            }


            if (index==-1){
                // changing ip to 255.255.0
                changedIp= getClassOfIp(ip);
                for (int i=0; i<routingTable.size();i++){
                    if (changedIp.equals(routingTable.get(i).getIp())  && routingTable.get(i).getMask()<=16){
                        index=i;
                    }
                }
            }
            // printing output

            if (ip.substring(0,3).equals("127")){
                output.println( ip + " is loopback; discarded");
            }
            else if (ip.substring(0,3).equals("255")){
                output.println( ip + " is malformed; discarded");
            }
            else if (index==-1){
                output.println( ip + genericIp);
            }
            else {
                output.println(ip + routingTable.get(index) );
                index=-1;
            }

        }
        output.close();


    }


    /*
        * Description: method used to decrease the class of the ip address. if it is 255.255.255.255 it will change to 255.255.255.0 etc
        * @param ip the ip that is needed to be changed to its higher class
        * @return newIp the new Ip address that is in the class higher than the one passed
     */
    public static String getClassOfIp(String ip) {
        String [] split = ip.split("\\.");
        StringBuffer newIp= new StringBuffer();
        for (int i=0; i<split.length-1;i++){
            newIp.append(split[i]+ ".");
        }
        newIp.replace(newIp.length()-1,newIp.length(),"");
        return newIp.toString();
    }





}
