package Main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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

			File arch= new File("./logs/log_"+torrentFile.getName()+"_"+localhost.getHostAddress()+".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(arch.getName(), true));
			writer.append(str+"\n");
			writer.close();
			System.out.println("El tiempo fue:"+ (timeF-timeI));


			DatagramSocket socket =  new DatagramSocket(4445);
			socket.setBroadcast(true);

			byte[] buffer = new String("done "+ localhost).getBytes();

			byte[] ipAddr = new byte[]{(byte) 157, (byte) 253, (byte) 205, 38};
			InetAddress addr = InetAddress.getByAddress(ipAddr);
			
			DatagramPacket packet  = new DatagramPacket(buffer, buffer.length, addr , 44444);
			socket.send(packet);

			buffer = new byte[256];

			packet  = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			String received = new String(packet.getData(), 0, packet.getLength());
			System.err.println(received);
			if(received.equals("final"))
			{
				client.stop();
				vaciarCarpeta(outputFile);
			}
			socket.close();

		}
		catch(ArrayIndexOutOfBoundsException e)
		{

			File torrentFile = new File("./archivos/torrents/local.torrent");
			File outputFile = new File("./archivos/nuevosarchivos");

			Date date= new Date();

			long timeI = date.getTime();
			client.downloadTorrent(
					torrentFile.getAbsolutePath(),
					outputFile.getAbsolutePath(),
					iPv4Address);
			Date dateF= new Date();

			long timeF = dateF.getTime();

			String str = "Fecha: "+date+" Cliente: "+ localhost+" demoró "+(timeF-timeI);
			File arch= new File("./logs/log_"+torrentFile.getName()+"_"+localhost.getHostAddress()+".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(arch.getName(), true));
			writer.append(str+"\n");
			writer.close();
			System.err.println("El tiempo fue:"+ (timeF-timeI));

			

			DatagramSocket socket =  new DatagramSocket(4445);
			socket.setBroadcast(true);

			byte[] buffer = new String("done " + localhost).getBytes();

			DatagramPacket packet  = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost() , 44444);
			socket.send(packet);

			buffer = new byte[256];

			packet  = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			String received = new String(packet.getData(), 0, packet.getLength());
			System.err.println(received);
			if(received.equals("final"))
			{
				client.stop();
				vaciarCarpeta(outputFile);
			}
			socket.close();
		}

	}

	private static void vaciarCarpeta(File outputFile) {	
		// TODO Auto-generated method stub
		File[] archivos=outputFile.listFiles();
		if(archivos!=null)
		{
			for (File file : archivos) {
				if(!file.delete())
				{
					vaciarCarpeta(file);
				}
			}
		}
		else
		{
			outputFile.delete();
		}
	}
}
