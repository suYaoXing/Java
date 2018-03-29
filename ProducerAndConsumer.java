

/**
 * @author nubia
 * @description 用wait()/notify()实现消费者生产者模型
 *
 */
public class ProducerAndConsumer {
	
	public static void main(String[] args) throws InterruptedException {
		Resource r = new Resource();
		Producer p = new Producer(r);
		Consumer c = new Consumer(r);
		Thread producer = new Thread(p);
		Thread consumer = new Thread(c);
		producer.start();
		consumer.start();
		Thread.sleep(1000);
	}
}


class Producer implements Runnable {

	Resource r;
	
	Producer(Resource r) {
		this.r = r;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		r.produce();
	}
}

class Consumer implements Runnable {

	Resource r;
	
	Consumer(Resource r) {
		this.r = r;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		r.consume();
	}
}


class Resource {
	
	private Object lock = new Object();
	
	private int count = 0;
	
	private volatile boolean flag = true;
	
	public void produce() {
		synchronized (lock) {
			
			while(true) {
				if(flag) {
				   count++;
				   flag = false;
				   System.out.println(Thread.currentThread().getName()+"生产者--"+count);
				   if(count == 1000)
						break;
				try {
					lock.wait();	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
				lock.notify();
			}
		}
	}
	
	public void consume() {
		synchronized (lock) {
			while(true) {
				if(!flag) {					
					flag = true;
					System.out.println(Thread.currentThread().getName()+"消费者--"+count);
					lock.notify();
				}
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
	}
}