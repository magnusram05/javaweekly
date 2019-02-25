package org.practice.java.multithreading;
import java.time.*;
import java.time.format.*;
import java.util.function.*;
import org.practice.java.multithreading.util.*;
public class HelloWorld {
	private Function<Void, String> currentTimeFunction = MultithreadingUtils.currentTimeFunction; 
	public void sayHello(String userName){
		String currentThreadName = Thread.currentThread().getName();
		String welcomeMessage = String.format("Hello, %s", userName);
		String welcomeMessageWithTS = String.format("Thread-[%10s] [%23s] %s", currentThreadName, 
			currentTimeFunction.apply(null), welcomeMessage);
		System.out.println(welcomeMessageWithTS);
		try{
			Thread.sleep(10000);
		} catch (InterruptedException ex){
			ex.printStackTrace();
		}
	}
}