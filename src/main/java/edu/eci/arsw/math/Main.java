/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.math;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author hcadavid
 */
public class Main {

    public static void main(String a[]) {
        System.out.println(bytesToHex(PiDigits.getDigits(3, 2000, 23)));

        System.out.println(PiDigits.getDigits(3, 500, 23).length);
        System.out.println("Without Threads.....................");
        System.out.println(bytesToHex(PiDigits.getDigits(0, 100)));
        System.out.println("Verification......");
        System.out.println(bytesToHex(PiDigits.getDigits(0, 100)).contentEquals(bytesToHex(PiDigits.getDigits(0, 100, 23))));

        /**System.out.println("Calculating with pause..................");
        Scanner scanner = new Scanner(System.in);

        while(true){
            try {
                Thread.sleep(5000);



            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }**/
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<hexChars.length;i=i+2){
            //sb.append(hexChars[i]);
            sb.append(hexChars[i+1]);            
        }
        return sb.toString();
    }

}
