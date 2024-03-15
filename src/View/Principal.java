package View;

import Controller.PingThread;

public class Principal {
	
	public static void main(String[] args) {
		if(isLinux()) {
			PingThread uol = new PingThread("UOL", "www.uol.com.br",10);
			PingThread terra = new PingThread("Terra", "www.terra.com.br",10);
			PingThread google = new PingThread("Google", "www.google.com.br",10);
			
			uol.start();
			terra.start();
			google.start();
			
			try {
				uol.join();
				terra.join();
				google.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Apenas SO Linux.");
		}
	}
	
	private static boolean isLinux() {
		String os = System.getProperty("os.name");
		return os.contains("Linux");
	}

}
