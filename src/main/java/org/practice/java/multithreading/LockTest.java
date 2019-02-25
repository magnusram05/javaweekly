package org.practice.java.multithreading;

import java.time.*;
import java.time.format.*;

public class LockTest { 
  
  public static void main(String [] args) throws Exception{
    Safe safe = new Safe();
    safe.open();
    System.out.println(String.format("[%16s][Thread %10s] has now %s the safe",
                                  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                  Thread.currentThread().getName(), "closed")); 

    Thread thread = new Thread(()->safe.open(),"Thread1");
    thread.start();

    thread.join();
    System.out.println(String.format("[%16s][Thread %10s] has now %s the safe",
                                  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                  thread.getName(), "closed"));  
  }
  
  static class Safe {
    public synchronized void open(){
      System.out.println(String.format("[%16s][Thread %10s] has now %s the safe",
                                  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                  Thread.currentThread().getName(), "opened"));      
    }
                                  
  }

}
