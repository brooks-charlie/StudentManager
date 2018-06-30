package main;

class ThreadDemo extends Thread {

    public void run() {
        for (int i=0; i<20; i++){
            System.out.println("Thread: " + Thread.currentThread().getId() + "number: " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
