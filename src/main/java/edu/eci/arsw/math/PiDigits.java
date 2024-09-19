package edu.eci.arsw.math;

import java.util.ArrayList;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;

    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        byte[] digits = new byte[count];
        double sum = 0;

        for (int i = 0; i < count; i++) {
            if (i % DigitsPerSum == 0) {
                sum = 4 * sum(1, start)
                        - 2 * sum(4, start)
                        - sum(5, start)
                        - sum(6, start);

                start += DigitsPerSum;
            }

            sum = 16 * (sum - Math.floor(sum));
            digits[i] = (byte) sum;
        }

        return digits;
    }

    // Method with threads
    public static byte[] getDigits(int start, int count, int threadNumber){
        int delta = start;
        int countPerThread = count / threadNumber;

        ArrayList<PiThread> threads = new ArrayList<PiThread>();
        ArrayList<Byte> digits = new ArrayList<Byte>();
        if(count % threadNumber == 0){
            for(int i = 0; i < threadNumber; i++){
                threads.add(new PiThread(start, countPerThread, new PiDigits()));
                start += countPerThread;
                threads.get(i).start();
            }
        }
        else{
            // System.out.println("Count Per Thread: " + countPerThread);
            for(int i = 0; i < threadNumber - 1; i++){
                threads.add(new PiThread(start, countPerThread, new PiDigits()));
                // System.out.println("Start: " + start);
                start += countPerThread;
                threads.get(i).start();
            }
            threads.add(new PiThread(start, count - start, new PiDigits()));
            threads.get(threadNumber - 1).start();
        }

        for(int i = 0; i < threadNumber; i++){
            try {
                threads.get(i).join();
                digits.addAll(threads.get(i).getDigits());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        byte[] finalDigits = new byte[count];

        for(int i = 0; i < count; i++){
            finalDigits[i] = digits.get(i);
        }

        return finalDigits;
    }

    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /// <summary>
    /// Return 16^p mod m.
    /// </summary>
    /// <param name="p"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }

}
