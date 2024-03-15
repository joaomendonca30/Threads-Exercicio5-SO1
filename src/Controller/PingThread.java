package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingThread extends Thread{

	private String nomeServidor;
	private String endereco;
	private int iteracoes;
	private long tempoTotal;
	
	public PingThread(String nomeServidor, String endereco, int iteracoes) {
		super();
		this.nomeServidor = nomeServidor;
		this.endereco = endereco;
		this.iteracoes = iteracoes;
	}
	
	@Override
	public void run () {
		try {
			for(int i = 0; i < iteracoes; i++) {
				ProcessBuilder pb = new ProcessBuilder("ping",
						"-4", "-c", "1", endereco);
				Process process = pb.start();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				while((line = buffer.readLine()) != null) {
					if(line.contains("time=")) {
						int index = line.indexOf("time=");
						String timeString = line.substring(index + 5);
						float tempo = Float.parseFloat(timeString.split(" ")[0]);
						tempoTotal += tempo;
						System.out.println("\n"+nomeServidor + " - Iteração " + (i + 1)+ " Tempo: "+ tempo + " ms");
					}
				}
				process.waitFor();
				buffer.close();
			}
		}
		catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
		float tempoMedio = tempoTotal / iteracoes;
		System.out.println("\n"+ nomeServidor + "- Tempo médio: " + tempoMedio + " ms");
	}
}
