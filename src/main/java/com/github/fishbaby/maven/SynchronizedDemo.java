package com.github.fishbaby.maven;

public class SynchronizedDemo {

    private boolean ready = false;
	private int result = 0;
	private int number = 1;

	public void write() {
		ready = true; // 1.1
		number = 2; // 1.2
	}

	public void read() {
		if (ready) { // 2.1
			result = number * 3; // 2.2
		}
		if(result==0){
			System.out.println("result结果" + result);
		}


	}

	private class ReadWriteThread extends Thread {
		private boolean flag;

		public ReadWriteThread(boolean flag) {
			this.flag = flag;
		}

		@Override
		public void run() {
			if (flag) {
				write();
			} else {
				read();
			}
		}
	}
	
	public static void main(String[] args) {
		SynchronizedDemo synDemo = new SynchronizedDemo();
		for (int i = 0; i < 1; i++) {
			synDemo.new ReadWriteThread(false).start();
			synDemo.new ReadWriteThread(true).start();
		}

	}
}