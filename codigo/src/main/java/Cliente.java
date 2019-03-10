import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;

import org.apache.log4j.BasicConfigurator;

import com.turn.ttorrent.client.SimpleClient;

public class Cliente {

	public static void main(String[] args) throws Exception {

		BasicConfigurator.configure();
	    SimpleClient client = new SimpleClient();
		InetAddress localhost = InetAddress.getLocalHost();


		Inet4Address iPv4Address = (Inet4Address) localhost;
		File torrentFile = new File("./archivos/torrents/prueba.torrent");
		File outputFile = new File("./archivos/downloads");

		client.downloadTorrent(
				torrentFile.getAbsolutePath(),
				outputFile.getAbsolutePath(),
				iPv4Address);
	}
}
