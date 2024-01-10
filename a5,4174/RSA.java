import java.util.ArrayList;
import java.util.Scanner;

public class RSA {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter the prime numbers p and q: ");
        int p = kb.nextInt();
        int q = kb.nextInt();

        int [][] keys = getPublicKey(p,q);


        System.out.print("Enter the plaintext message m (an integer): ");
        int m = kb.nextInt();
        System.out.println("Encrypting m...");
        int c=  fastModular(m,keys[0][0],keys[0][1]);
        System.out.println("The ciphertext c is " + c);
       m= fastModular(c,keys[1][0],keys[1][1]);
        System.out.println("The plaintext c is " + m);
    }

    public static int gcd (int a, int b){
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }


    public static int[][] getPublicKey(int p, int q) {
        int [][] keys= new int[2][2];
        //step 2
        int n = p *q;
        int pq_1 = (p-1)*(q-1);
        int e=0;
        // step 3
        for (int i=2; i<pq_1;i++){
            if (gcd(i,pq_1)==1){
                e= i;
                break;
            }
        }

        //step 4
        boolean foundD=false;
        int inflatedpq_1=pq_1 +1;
        int d=0;
        int multi = 2;
        while (!foundD){
            if (inflatedpq_1%e==0){
              d= inflatedpq_1;
              foundD=true;
            }

            inflatedpq_1= pq_1*multi+1;
            multi++;
        }

        keys[0][0]= e;
        keys[0][1]=n;
        keys[1][0]= d/e;
        keys[1][1]= n;

        System.out.println("Public RSA key is ("+ keys[0][0]+ ", "+ keys[0][1]+")");
        System.out.println("Private RSA key is ("+ keys[1][0]+ ", "+ keys[1][1]+")");
        return keys;
    }

    public static int encrypt(int [][] keys,int m){

        ArrayList<Integer> powersOfTwo = new ArrayList<>();
        powersOfTwo.add(1);
        for (int i=1;i<14;i++){

            powersOfTwo.add((int) Math.pow(2,i));
        }

        int c=1;
       int tempE=keys[0][0];
        ArrayList<Integer> powersUsed= new ArrayList<>();
        for (int i=13; i>=0;i--){
            if (tempE-powersOfTwo.get(i) >=0){
                powersUsed.add(powersOfTwo.get(i));
               tempE-= powersOfTwo.get(i);
         //       i++;
            }
        }

        for (int i=powersUsed.size()-1; i>=0;i--){
            int temp = (int) Math.pow(m,powersUsed.get(i));
            c= c* ( temp%keys[0][1])  ;
        }
        c= c%keys[0][1];
        return c;
    }

    /*
        * this method's implementation was influenced by Geeks for geeks modular exponential code
        * url: https://www.geeksforgeeks.org/modular-exponentiation-power-in-modular-arithmetic/
    */
    public static int fastModular(int m, int d, int n ){
        int result = 1;
        while (d > 0) {
            if (d % 2 == 1) {
                result = (result * m) % n;
            }
            m = (m * m) % n;
            d /= 2;
        }
        return result;

    }
}
