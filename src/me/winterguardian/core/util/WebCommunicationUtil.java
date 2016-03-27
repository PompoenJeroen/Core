package me.winterguardian.core.util;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class WebCommunicationUtil
{
	private WebCommunicationUtil(){}
	
	public static String get(String url) throws IOException
	{
		String source = "";
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						new URL(url).openConnection().getInputStream()));
		String inputLine;
		 
		while ((inputLine = in.readLine()) != null)
			source += inputLine;
		in.close();
		return source;
	}
	
	public static String post(String address, Map<String, String> data, HashMap<String, String> properties) throws Exception
	{
		String result = "";
		OutputStreamWriter writer = null;
		BufferedReader reader = null;
		try
		{
			String serializedData = "";
			for(int i = 0; i < data.keySet().size(); i++)
			{
				if (i != 0) 
					serializedData += "&";
				serializedData += URLEncoder.encode((String) data.keySet().toArray()[i], "UTF-8") + "=" + URLEncoder.encode(data.get(data.keySet().toArray()[i]), "UTF-8");
			}
			
			
			URLConnection conn = new URL(address).openConnection();
			conn.setDoOutput(true);
			if(properties != null)
				for(Map.Entry<String, String> entry : properties.entrySet())
					conn.setRequestProperty(entry.getKey(), entry.getValue());

			writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(serializedData);
			writer.flush();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String ligne;
			while ((ligne = reader.readLine()) != null)
			{
				result += ligne;
			}
			writer.close();
			reader.close();
		}
		catch(Exception e)
		{
			try
			{
				writer.close();
			}
			catch(Exception e2) {}
			
			try
			{
				reader.close();
			}
			catch(Exception e3) {}
			
			throw e;
		}

		return result;
	}

	public static String post(String address, Map<String, String> data) throws Exception
	{
		return post(address, data, null);
	}

	public static void sendPostData(String adress, Map<String, String> data) throws Exception
	{
		OutputStreamWriter writer = null;
		try
		{
			String serializedData = "";
			for(int i = 0; i < data.keySet().size(); i++)
			{
				if (i != 0) 
					serializedData += "&";
				serializedData += URLEncoder.encode((String) data.keySet().toArray()[i], "UTF-8") + "=" + URLEncoder.encode(data.get(data.keySet().toArray()[i]), "UTF-8");
			}
			
			
			URLConnection conn = new URL(adress).openConnection();
			conn.setDoOutput(true);

			writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(serializedData);
			writer.flush();
			
			writer.close();
		}
		catch(Exception e)
		{
			try
			{
				writer.close();
			}
			catch(Exception e2) {}
			
			throw e;
		}
	}

	public static void main(String[] args) throws Exception
	{
		System.out.print(get("http://www.serveurs-minecraft.org/vote.php?id=24186"));
		System.out.println();
		System.out.println();
		Thread.sleep(1000);
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		HashMap<String, String> data = new HashMap<>();
		data.put("confirmation", "true");

		String result = "";
		OutputStreamWriter writer = null;
		BufferedReader reader = null;
		try
		{
			String serializedData = "";
			for(int i = 0; i < data.keySet().size(); i++)
			{
				if (i != 0)
					serializedData += "&";
				serializedData += URLEncoder.encode((String) data.keySet().toArray()[i], "UTF-8") + "=" + URLEncoder.encode(data.get(data.keySet().toArray()[i]), "UTF-8");
			}


			HttpURLConnection conn = (HttpURLConnection)new URL("http://www.serveurs-minecraft.org/vote.php?id=24186").openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4");
			conn.setRequestProperty("Cache-Control", "max-age=0");
			conn.setRequestProperty("Origin", "http://www.serveurs-minecraft.org");
			conn.setRequestProperty("Referer", "http://www.serveurs-minecraft.org/vote.php?id=24186");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 ");
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
			conn.setAllowUserInteraction(true);
			conn.connect();

			writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(serializedData);
			writer.flush();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String ligne;
			while ((ligne = reader.readLine()) != null)
			{
				result += ligne;
			}
			writer.close();
			reader.close();
		}
		catch(Exception e)
		{
			try
			{
				writer.close();
			}
			catch(Exception e2) {}

			try
			{
				reader.close();
			}
			catch(Exception e3) {}

			throw e;
		}

		System.out.print(result.replace("<br>", "\n\n"));
	}
}
