
import java.io.IOException;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Random;

//In this program we assumed that the eavesdropper Eve has captured 
//p, G, and NaG. It is left to compute Na using the baby step giant step
// algorithm
public class Baby_Step_Giant_step {

	// For more information about the diffie-hellman key exchange
	// check https://en.wikipedia.org/wiki/Diffie%E2%80%93Hellman_key_exchange
	public static void main(String[] args) throws IOException {

		// The fixed prime P
		BigInteger P = new BigInteger("1000253");
		// The generator G
		BigInteger G = new BigInteger("1000037");

		// this array will hold a 100 values ( potential secret values from alice)
		BigInteger a[] = new BigInteger[100];

		// generate the values randomly
		for (int i = 0; i < a.length; i++) {
			a[i] = getRandomBigInteger();
		}

// This is the value that Alice sends to Bob
// Compute all the valuesA[i] for each private key a[i]generated. 
		BigInteger A[] = new BigInteger[100];
		for (int i = 0; i < A.length; i++) {
			A[i] = G.modPow(a[i], P);
		}

		// For each key sent to Bob, compute the secret key which consists of solving
		// the
		// discrete logarithm problem
		for (int i = 0; i < A.length; i++) {
			long start = System.nanoTime();
			// to solve the discrete logarithm we use the giant step baby step
			// algorithm
			BigInteger an = logBabyStepGiantStep(G, A[i], P);

			long endtime = System.nanoTime();
			// display the time took to solve the discrete logarithm for each iteration
			System.out.println((endtime - start) / 1000000);
		}

	}

	/**
	 * The implementation of the algorithm baby step giant step can also be found at
	 * http://dinus.ac.id/repository/docs/ajar/Introduction_to_Cryptography_with_Java_Applets.pdf
	 * 
	 * @param G       the generator G
	 * @param residue is
	 * @param P       the fixed prime P
	 * @return the secret value of Alice ( a)
	 */
	public static BigInteger logBabyStepGiantStep(BigInteger G, BigInteger residue, BigInteger P) {

		// This algorithm solves base^x = residue (mod modulus) for x using baby step
		// giant step
		BigInteger m = sqrt(P).add(BigInteger.ONE);
		Hashtable<BigInteger, BigInteger> h = new Hashtable<>();
		BigInteger basePow = BigInteger.valueOf(1);
		for (BigInteger j = BigInteger.valueOf(0); j.compareTo(m) < 0; j = j.add(BigInteger.ONE)) {
			h.put(basePow, j);
			basePow = basePow.multiply(G).mod(P);

		}

		BigInteger basetotheinv = G.modPow(m, P).modInverse(P);
		BigInteger y = new BigInteger(residue.toByteArray());

		BigInteger target;
		for (BigInteger i = BigInteger.valueOf(0); i.compareTo(m) < 0; i = i.add(BigInteger.ONE)) {
			target = (BigInteger) h.get(y);
			if (target != null)
				return i.multiply(m).add(target);
			y = y.multiply(basetotheinv).mod(P);

		}
		return BigInteger.valueOf(-1);
	}

	/**
	 * 
	 * @param x a bing integer value
	 * @return the square root of a big integer
	 */
	public static BigInteger sqrt(BigInteger x) {
		BigInteger div = BigInteger.ZERO.setBit(x.bitLength() / 2);
		BigInteger div2 = div;
		// Loop until we hit the same value twice in a row, or wind
		// up alternating.
		for (;;) {
			BigInteger y = div.add(x.divide(div)).shiftRight(1);
			if (y.equals(div) || y.equals(div2))
				return y;
			div2 = div;
			div = y;
		}
	}

	/**
	 * 
	 * @return a random big integer the upper bound is the fixed prime p
	 */
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
