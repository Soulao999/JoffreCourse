package fr.joffre.tpe.joffrecourse;

import android.util.Log;
import android.widget.Button;


public class Timer extends Thread{
    public static int seconde = 0;
    public static boolean running = true;
    public Timer(String name) {
        super(name);
    }
    public void run() {

        while(running){
            try {
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconde++;
        }
    }
    public static int[] convert(int sec){

        int min = sec/60;
        sec = sec%60;
        int h = min/60;
        min = min%60;
        Log.i("Convert","Heures: "+String.valueOf(h)+ " Minutes: "+String.valueOf(min)+" Secondes: "+ String.valueOf(sec));
        int Result[] = {h,min,sec};
        return Result;
    }
    public static void arret(){
        running = false;
    }
}
