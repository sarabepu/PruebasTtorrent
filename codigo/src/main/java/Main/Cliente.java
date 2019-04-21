package Main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;

import com.turn.ttorrent.client.SimpleClient;

public class Cliente {

	public static void main(String[] args) throws Exception {

		BasicConfigurator.configure();
		SimpleClient client = new SimpleClient();
		InetAddress localhost = InetAddress.getLocalHost();


		Inet4Address iPv4Address = (Inet4Address) localhost;

		try{
			File torrentFile = new File(args[0]);
			File outputFile = new File(args[1]);
			Date date= new Date();

			long timeI = date.getTime();
			client.downloadTorrent(
					torrentFile.getAbsolutePath(),
					outputFile.getAbsolutePath(),
					iPv4Address);
			Date dateF= new Date();

			long timeF = dateF.getTime();

			String str = "Fecha: "+date+" Cliente: "+ localhost+" demoró "+(timeF-timeI);
			BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Profesor/unaCloudTorrent/log.txt", true));
			writer.append(str+"\n");
			writer.close();
			System.out.println("El tiempo fue:"+ (timeF-timeI));

			vaciarCarpeta(outputFile);


		}
		catch(ArrayIndexOutOfBoundsException e)
		{

			File torrentFile = new File("./archivos/torrents/ultimoexterno.torrent");
			File outputFile = new File("./archivos/nuevosarchivos");

			Date date= new Date();

			long timeI = date.getTime();
			client.downloadTorrent(
					torrentFile.getAbsolutePath(),
					outputFile.getAbsolutePath(),
					iPv4Address);

			Date dateF= new Date();
			client.stop();
			long timeF = dateF.getTime();

			String str = "Fecha: "+date+" Cliente: "+ localhost+" demoró "+(timeF-timeI);
			BufferedWriter writer = new BufferedWriter(new FileWriter("./archivos/log.txt", true));
			writer.append(str+"\n");
			writer.close();
			System.out.println("El tiempo fue:"+ (timeF-timeI));

			vaciarCarpeta(outputFile);

		}

	}

	private static void vaciarCarpeta(File outputFile) {	
		// TODO Auto-generated method stub
		File[] archivos=outputFile.listFiles();
		for (File file : archivos) {
			if(!file.delete())
			{
				vaciarCarpeta(file);
			}
		}
	}
}
