import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class main {

    public static void main (String args[]) throws FileNotFoundException {

        // to run code, just edit line 11 and 49 to include the text files needed to be read. program will identify if its standard or extended on its own
        try {
            File aclText = new File("acl.txt");
            Scanner aclFile = new Scanner(aclText);
            ArrayList<Acl> aclRules = new ArrayList<>();
            boolean decision=false;
            while (aclFile.hasNextLine()){
                Acl acl;
                String line = aclFile.nextLine();
                line = line.replaceAll("\\s+"," ");

                String [] split = line.split(" ");
                if (split[0].equals("interface")){
                    break;
                }
                if (split[2].equals("deny")){
                     decision= false;
                }
                else{
                    decision= true;
                }

                if (split.length>9){
                    acl= new Acl(Integer.parseInt(split[1]),decision,split[4],split[5]);
                    acl.setDestinationIp(split[split.length-4]);
                    acl.setPort(split[split.length-1]);
                    acl.setDestinationMask(split[split.length-3]);
                }
                else if (split.length>6){
                    acl= new Acl(Integer.parseInt(split[1]),decision,split[4],split[5]);
                    acl.setDestinationIp(split[split.length-2]);
                    acl.setDestinationMask(split[split.length-1]);
                }
                else{
                    acl= new Acl(Integer.parseInt(split[1]),decision,split[3],split[4]);
                }
                aclRules.add(acl);

            }
            aclFile.close();
            File packetsTxt= new File("packets.txt");
            Scanner packets = new Scanner(packetsTxt);

            while (packets.hasNextLine()){
                String line= packets.nextLine();

                line = line.replaceAll("\\s+"," ");

                String [] split = line.split(" ");
                decision = false;
                for (Acl acl: aclRules) {
                    if (checkIps(acl,split,acl.getSourceMask())) {
                        if (acl.isDecision()){
                            decision = true;
                            break;
                        }
                        else{
                            decision = false;
                            break;

                        }

                    }


                }

                if (split.length==1){
                    if (decision){
                        System.out.println("permit packet from" + split[0]);
                    }
                    else{
                        System.out.println("deny packet from" + split[0] );
                    }
                }
                else{
                    if (decision){
                        System.out.println("permit packet from" + split[0] + " to " + split[1]);
                    }
                    else{
                        System.out.println("deny packet from" + split[0] + " to " + split[1]);
                    }
                }

            }

        }
        catch (FileNotFoundException e){
            System.out.println("error");
            e.printStackTrace();
        }



    }

    public static boolean checkIps(Acl acl, String [] info,String mask){

            String newIp= acl.getSourceIp();
            // get ip by removing the last unneeded four bytes for checking
            String [] split = newIp.split("\\.");
            String [] srcPacket= info[0].split("\\.");
            int count = getComparisionBytes(mask);

            if (info.length>1){
                String [] dstPacketIp = info[1].split("\\.");
                if (!acl.getDestinationMask().equals("standard")){

                    String [] aclDst = acl.getDestinationIp().split("\\.");
                    String [] dstPacket = info[1].split("\\.");
                    boolean correctSource= true;
                    for (int i=0; i<count;i++){
                        if (!split[i].equals(srcPacket[i])){
                            correctSource= false;
                        }
                    }
                    if (correctSource){
                        count= getComparisionBytes(acl.getDestinationMask());

                        for (int i=0; i<count;i++){
                            if (!aclDst[i].equals(dstPacket[i])){
                               return false;
                            }
                        }
                        if (acl.getPort()!=null){
                            if (acl.getPort().equals("20-21")){
                                if (info[2].equals("20") ||info[2].equals("21")  ){
                                    return true;
                                }
                            }
                            if (acl.getPort().equals(info[2])){
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                        else {
                            return true;
                        }



                    }

                }
            }




            for (int i=0; i<count;i++){
                if (!split[i].equals(srcPacket[i])){
                    return false;
                }
            }
            return true;


            }

        public static int getComparisionBytes (String mask1){
            int splitCount;
            if (mask1.equals("0.0.0.0")){
                splitCount=4;
            }
            else if (mask1.equals("0.0.0.255")){
                splitCount=3;
            }
            else {
                splitCount= 2;
            }
            return splitCount;

        }







    }



