/*
    Name: Abdulrahman AL Kindi
    Banner ID: B00858783
 */

import java.util.Scanner;

public class CRC {


    public static String crcDivision(String mx,String gx){
        String zeros="";
        for (int i=0; i<gx.length()-1;i++){
            zeros+='0';
        }
       String remainder= division(mx+zeros, gx);

        String output = mx+ remainder;
        return output;
    }

   public static String senderCheck(String px,String gx){
        String remainder= division(px,gx);
        boolean error=false;
        for (int i=0; i<remainder.length();i++){
            if (remainder.charAt(i)!='0'){
                error=true;
            }
        }
        if (error){
            return "Error caught";
        }
        return "no error";
    }
    public static void main (String []arg){

        System.out.print("IF sender Enter 0, IF receiver Enter 1: ");
        System.out.println("");
        Scanner kb = new Scanner(System.in);
        int type= Integer.parseInt( kb.nextLine());
        if (type==0){
            System.out.print("Please Enter mx: ");
            String mx= kb.nextLine();
            System.out.println("");
            System.out.print("Please Enter gx: ");
            String gx= kb.nextLine();
            System.out.println("");
           System.out.println("Message sent is: "+ crcDivision(mx,gx));

        }
        else {
            System.out.print("Please Enter px: ");
            String px= kb.nextLine();
            System.out.println("");
            System.out.print("Please Enter gx: ");
            String gx= kb.nextLine();
            System.out.println("");
            System.out.println(senderCheck(px,gx));
        }
     //   System.out.println(crcDivision("10110011","11001"));

    }

    public static String division(String mx, String gx){
        String zeros="";
        for (int i=0; i<gx.length()-1;i++){
            zeros+='0';
        }
        String px2= mx;
        StringBuffer answer= new StringBuffer();
        String divisor;
        if (px2.charAt(0)=='1'){
            answer.append('1');
            divisor=gx.substring(1);
        }
        else{
            answer.append('0');
            divisor= zeros;
        }
        String bitToDivide= px2.substring(1,divisor.length()+1);
        // getting the index of mx that we will need to insert into the next iteration's xor operation
        int index= gx.length();
        String result="";
        while (index<=px2.length()){
            result="";
            for (int i=0;i<gx.length()-1;i++){
                result+= bitToDivide.charAt(i) ^ divisor.charAt(i);

            }
            if (index<px2.length()) {
                answer.append(result.charAt(0));
                bitToDivide = result.substring(1) + px2.charAt(index);

                if (result.charAt(0) == '0') {
                    divisor = zeros;
                } else {
                    divisor = gx.substring(1);
                }
            }
            index++;

        }
        return result ;
    }
}
