public class ProducerConsumer {
	public static void main(String []  arg) {
        Intermediary intermediary = new Intermediary();
        Thread producer = new Producer(intermediary);
	    Thread consumer = new Consumer(intermediary);
	    producer.start();
	    consumer.start();
	}

	static class Intermediary {
		private Integer msg;
		private Boolean hasMoreMessage = true;
		public Integer getMsg(){
			return this.msg;
		}
		public void setMsg(Integer msg){
			this.msg = msg;
		}
		public void setHasNoMoreMessage(){
			this.hasMoreMessage = false;
		}
		public boolean hasMoreMessage(){
			return this.hasMoreMessage;
		}
	}

	static class Consumer extends Thread {
		Intermediary intermediary;
		Consumer(Intermediary intermediary){
			this.intermediary = intermediary;
		}
		public void run(){
			synchronized(intermediary){
				try{
					    while(true){			    
		                    if(intermediary.getMsg() == null){
						        intermediary.wait();
					        } else {
							    System.out.println(intermediary.getMsg());
							    intermediary.setMsg(null);
							    intermediary.notify();
							    if (intermediary.hasMoreMessage())
							        intermediary.wait();
							    else{
									break;
							    }
							}
					    }
				} catch (InterruptedException ex){
					System.out.println(ex);
				}
			}
		}
	}

	static class Producer extends Thread {
		Intermediary intermediary;		
		Producer(Intermediary intermediary){
			this.intermediary = intermediary;
		}
		public void run(){
			synchronized(intermediary){
				try {
					    for(int i=0;i<50;i++){
						if(intermediary.getMsg() != null){
							intermediary.wait();
						}
						else {
						    intermediary.setMsg(i);
						    intermediary.notify();
						    if (i != 49) {
	   					        intermediary.wait();
						    } else {
							    intermediary.setHasNoMoreMessage();
						    }
						}
				    }  
				} catch (InterruptedException ex){
					System.out.println(ex);
				}
			}
		}
	}
}
