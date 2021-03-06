package fr.joffre.tpe.joffrecourse;

import java.lang.Runnable;

public class Timer extends Thread{
    public static int seconde = 0;
    public static boolean running = true;
    public static String tim;
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
        int Result[] = {h,min,sec};
        tim = h+"h"+min+"min"+sec+"sec";
        return Result;
    }
    public static void arret(){
        running = false;
    }
}
