package edu.eci.arsw.math;

import java.util.ArrayList;

public class PiThread extends Thread {
    private int start;
    private int count;
    private PiDigits piDigits;
    private ArrayList<Byte> digits;

    public PiThread(int start, int count, PiDigits piDigits){
        this.start = start;
        this.count = count;
        this.piDigits = piDigits;
        this.digits = new ArrayList<Byte>();
    }

    public void run(){
        byte[] calculatedDigits = piDigits.getDigits(start, count);
        for(int i = 0; i < count; i++){
            digits.add(calculatedDigits[i]);
        }
        // System.out.println(this.getName() + " calculated digits " + digits);
    }

    public ArrayList<Byte> getDigits(){
        return digits;
    }
}
