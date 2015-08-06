import java.io.IOException;

import Thread.ServerRunnable;

public class Main {

	public static void main(String[] args) {

		try {

			ServerRunnable serverRunnable = new ServerRunnable(6000);
			Thread serverThread = new Thread(serverRunnable);
			serverThread.start();
			try {
				serverThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
