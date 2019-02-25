package org.practice.java.multithreading.util;
import java.time.*;
import java.time.format.*;
import java.util.function.*;

public class MultithreadingUtils {
	public static Function<Void, String> currentTimeFunction
		= (Void t) -> { 
			LocalDateTime time = LocalDateTime.now(); 
			return time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		};
}