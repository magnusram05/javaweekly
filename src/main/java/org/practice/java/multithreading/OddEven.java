/*1) Create odd and even threads
2) Start them one after the other
3) Odd thread should start first 
4) Use an external lock and pass it to both the threads
- synchronize the for loop on lock
- lock.notify for even to start
- lock.wait till even releases the lock

Countdown latch(1), countdown.await inside even run method before synchronized block
countdown.countdown after printing 1 from odd thread*/
package org.practice.java.multithreading;
import java.util.concurrent.*;

public class OddEven {
	public static void main(String [] args){
	    Object lock = new Object();
	    CountDownLatch latch = new CountDownLatch(1);
        Thread oddThread = new OddThread(lock, latch);
        Thread evenThread = new EvenThread(lock, latch);
        oddThread.start();
        evenThread.start();
		try{
			oddThread.join();
		} catch (InterruptedException ex){
			System.out.println(ex);
		}
	}

	static class OddThread extends Thread {
	    private Object lock;
	    private CountDownLatch latch;
	    OddThread(Object lock, CountDownLatch latch){
            this.lock = lock;
            this.latch = latch;
	    }

	    public void run(){
	        synchronized(lock){
	            for(int i=1;i<10;i=i+2){
	                System.out.println("Odd thread" + i);
	                latch.countDown();
	                try {	                
	                    lock.notify();
	                    lock.wait();
	                } catch(InterruptedException ex){
	                	System.out.println(ex);
	                }
	            }
	        }
	    }
	}

    static class EvenThread extends Thread {
        private Object lock;
	    private CountDownLatch latch;	    
	    EvenThread(Object lock, CountDownLatch latch){
            this.lock = lock;
            this.latch = latch;	        
	    }
	    public void run(){
	    	try{
	            latch.await();
	        } catch (InterruptedException ex){
	        	System.out.println(ex);
	        }
	        synchronized(lock){
	            for(int i=2;i<10;i=i+2){
	                System.out.println("Even thread" + i);
	                try {	                
	                    lock.notify();
	                    lock.wait();
	                } catch(InterruptedException ex){
	                	System.out.println(ex);
	                }
	            }
	        }
	    }	    
	}
}