/*1) Create odd and even threads
2) Start them one after the other
3) Odd thread should start first 
4) Use an external lock and pass it to both the threads
- synchronize the for loop on lock
- lock.notify for even to start
- lock.wait till even releases the lock

Countdown latch(1), countdown.await inside even run method before synchronized block
countdown.countdown after printing 1 from odd thread*/
/*
Custom lock with booleans oddProceed=true, evenProceed=false
Odd thread checks oddProceed, prints, sets evenProceed to true, notifies and waits
Even thread checks evenProceed, prints, notifies and waits
*/
package org.practice.java.multithreading;
import java.util.concurrent.*;

public class OddEven {
	public static void main(String [] args){
		doWithCountdownLatch();
	}

	private static void doWithCountdownLatch(){
        Object lock = new Object();
	    CountDownLatch latch = new CountDownLatch(1);
        Thread oddThread = new OddThread(lock, latch);
        Thread evenThread = new EvenThread(lock, latch);
        oddThread.start();
        evenThread.start();
		try{
			evenThread.join();
		} catch (InterruptedException ex){
			System.out.println("Main thread interrupted while waiting for Odd thread to finish execution"+ ex.getMessage());
		}
	}

	private static void doWithVolatile(){

	}

	static class CustomLock {
		private volatile boolean evenProceed = false;
		public void setEvenProceed(boolean isProceed){
			evenProceed = isProceed;
		}
	}

	static class OddThread extends Thread {
	    private final Object lock;
	    private final CountDownLatch latch;
	    OddThread(Object lock, CountDownLatch latch){
            this.lock = lock;
            this.latch = latch;
	    }

	    public void run(){
	        synchronized(lock){
	            for(int i=1;i<20;i=i+2){
	                System.out.println("Odd thread " + i);
	                latch.countDown();
	                try {	                
	                    lock.notify();
	                    lock.wait();
	                } catch(InterruptedException ex){
	                	System.out.println("Odd thread interrupted while waiting for Even thread notification"+ ex.getMessage());
	                }
	            }
	        }	        
	    }
	}

    static class EvenThread extends Thread {
        private final Object lock;
	    private final CountDownLatch latch;	    
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
	            for(int i=2;i<=20;i=i+2){
	                System.out.println("Even thread" + i);
	                try {	                
	                    lock.notify();
	                    if(i != 20)
	                        lock.wait();
	                } catch(InterruptedException ex){
	                	System.out.println("Even thread interrupted while waiting for Odd thread notification"+ ex.getMessage());
	                }
	            }
	        }
	    }	    
	}
}

