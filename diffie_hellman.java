import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class diffie_hellman {

	public static void main(String[] args) throws IOException {
		
		
	     
		    BufferedWriter writer = new BufferedWriter(new FileWriter("brute_force.txt"));
//		    writer.write(fileContent);
//		    writer.close();

		

		// this program implements the diffie-hellman protocol
		// below is the protocol to implement the diffie hellman protocol
		/**
		 * 1. Either Alice or Bob selects a large prime p and a primitive root g mod p.
		 * Both p and g are made public. 2. Alice chooses a secret random number a with
		 * 1 <a< p-2, and Bob does the same, selects a random number b with 1<b< p-2. 3.
		 * Alice sends A= g^a (mod p) to Bob and Bob sends B= g^b (mod p) to Alice.
		 * Using the messages each one received, they can compute the private key K.
		 * 4.Alice calculates K by K=( gb)a ( mod p) and Bob calculates K by K=( ga)b
		 * 
		 */

		// in this program we simulate

		// What is known
//		    P=941
		BigInteger P = new BigInteger("1000253");
//		long P = 941;
//		G=627
		BigInteger G = new BigInteger("1000037");
//		long G = 627;

		Random rand = new Random();

		// What is not known
		BigInteger a[] = new BigInteger[100];

		for (int i = 0; i < a.length; i++) {
			a[i] = getRandomBigInteger();
		}
//		int a = 347;
		BigInteger b[] = new BigInteger[100];

		for (int i = 0; i < a.length; i++) {
			b[i] = getRandomBigInteger();
		}
//		BigInteger b = new BigInteger("781");
//		int b = 781;
//		BigInteger KEY = new BigInteger("470");
//		long KEY = 470;
		// Alice computes
//		b.po

		BigInteger A[] = new BigInteger[100];
		for(int i=0; i<A.length; i++) {
			A[i]=G.modPow(a[i], P);
		}
		
		BigInteger B[] = new BigInteger[100];
		for(int i=0; i<A.length; i++) {
			B[i]=G.modPow(b[i], P);
		}
		// Bob computes
//		BigInteger B = G.modPow(b, P);

		// so A and B are not konwn

		// Brute force
		long Answer = 0;
		boolean value []= new boolean[100];
		BigInteger x = new BigInteger("1");
		BigInteger potential_a = BigInteger.ZERO;
		BigInteger potential_b = BigInteger.ZERO;
//		long start= System.nanoTime();
		for(int i=0; i<A.length; i++) {
			long start= System.nanoTime();
			BigInteger an=BruteForce(A[i], B[i], P, G);
			writer.write(an.toString());
//			System.out.println(an);
			long endtime=System.nanoTime();
			writer.write("Time "+(i+1)+" "+(endtime-start)/1000000);
			System.out.println((endtime-start)/1000000);
//			System.out.println(an);
		}
		System.out.println("The End");
		
		writer.close();
		
		
		
		

	}
	
	public static BigInteger BruteForce(BigInteger A, BigInteger B, BigInteger P, BigInteger G) {
		boolean value =false;
		BigInteger x = new BigInteger("1");
		BigInteger potential_a = BigInteger.ZERO;
		BigInteger potential_b = BigInteger.ZERO;
		while (!value) {

			if (G.modPow(x, P).equals(A)) {
				potential_a = x;
				value = true;
				break;
			}
			if (G.modPow(x, P).equals(B)) {
				potential_b = x;
				value = true;
				break;
			}

			x = x.add(BigInteger.ONE);
		}

		BigInteger Potential_KEY = A.modPow(x, P);
		return Potential_KEY;

	}

	public static BigInteger getRandomBigInteger() {
		Random rand = new Random();
		BigInteger upperLimit = new BigInteger("1000253");
		BigInteger result;
		do {
			result = new BigInteger(upperLimit.bitLength(), rand); // (2^4-1) = 15 is the maximum value
		} while (result.compareTo(upperLimit) >= 0); // exclusive of 13
		return result;
	}

}
