package main;

public class RunnableDemo implements Runnable {

    @Override
    public void run() {
        for (int i=0; i<20; i++){
            System.out.println("Runnable: " + Thread.currentThread().getId() + "number: " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}