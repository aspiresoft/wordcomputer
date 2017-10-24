package nesne;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Wiki sayfalari okunuyor.\nLutfen bekleyin\n========================================\n");

		String turkceSiteler[] = new String[] { "https://tr.wikipedia.org/wiki/%C4%B0stanbul", "https://tr.wikipedia.org/wiki/Dil_(filoloji)",
						"https://tr.wikipedia.org/wiki/%C4%B0nsan", "https://tr.wikipedia.org/wiki/Tarih",
						"https://tr.wikipedia.org/wiki/T%C3%BCrkiye", "https://tr.wikipedia.org/wiki/Fransa",
						"https://tr.wikipedia.org/wiki/%C4%B0talya", "https://tr.wikipedia.org/wiki/Roma_%C4%B0mparatorlu%C4%9Fu",
						"https://tr.wikipedia.org/wiki/Osmanl%C4%B1_%C4%B0mparatorlu%C4%9Fu",
						"https://tr.wikipedia.org/wiki/Mustafa_Kemal_Atat%C3%BCrk", };
		String ingilizceSiteler[] = new String[] { "https://en.wikipedia.org/wiki/Mustafa_Kemal_Atat%C3%BCrk",
						"https://en.wikipedia.org/wiki/Republican_People%27s_Party_(Turkey)", "https://en.wikipedia.org/wiki/Turkish_people",
						"https://en.wikipedia.org/wiki/Africa", "https://en.wikipedia.org/wiki/Istanbul", "https://en.wikipedia.org/wiki/China",
						"https://en.wikipedia.org/wiki/South_Asia", "https://en.wikipedia.org/wiki/World_War_I",
						"https://en.wikipedia.org/wiki/League_of_Nations", "https://en.wikipedia.org/wiki/Haiti" };

		String almacaSiteler[] = new String[] { "https://de.wikipedia.org/wiki/Osmanisches_Reich",
						"https://de.wikipedia.org/wiki/Mustafa_Kemal_Atat%C3%BCrk", "https://de.wikipedia.org/wiki/Berlin",
						"https://de.wikipedia.org/wiki/Deutsche_Sprache", "https://de.wikipedia.org/wiki/Franz%C3%B6sische_Sprache",
						"https://de.wikipedia.org/wiki/Kanada", "https://de.wikipedia.org/wiki/Volkswagen",
						"https://de.wikipedia.org/wiki/T%C3%BCrkei", "https://de.wikipedia.org/wiki/Adolf_Hitler",
						"https://de.wikipedia.org/wiki/M%C3%BCnchen" };

		String ispanyolcaSiteler[] = new String[] { "https://es.wikipedia.org/wiki/Idioma_espa%C3%B1ol", "https://es.wikipedia.org/wiki/Lat%C3%ADn",
						"https://es.wikipedia.org/wiki/Idioma_ingl%C3%A9s", "https://es.wikipedia.org/wiki/Alemania",
						"https://es.wikipedia.org/wiki/Australia", "https://es.wikipedia.org/wiki/Madrid", "https://es.wikipedia.org/wiki/Barcelona",
						"https://es.wikipedia.org/wiki/Valencia", "https://es.wikipedia.org/wiki/Sevilla",
						"https://es.wikipedia.org/wiki/M%C3%A1laga" };

		// Kelimeleri tutacak olan veri yapilari
		HashMap<String, Integer> turkceKelimeler = new HashMap<String, Integer>();
		HashMap<String, Integer> ingilizceKelimeler = new HashMap<String, Integer>();
		HashMap<String, Integer> almancaKelimeler = new HashMap<String, Integer>();
		HashMap<String, Integer> ispanyolcaKelimeler = new HashMap<String, Integer>();

		// TriGram helimelerini tutacak olan veri yapilari
		HashMap<String, Integer> turkceTriGramKelimeler = new HashMap<String, Integer>();
		HashMap<String, Integer> ingilizceTriGramKelimeler = new HashMap<String, Integer>();
		HashMap<String, Integer> almancaTriGramKelimeler = new HashMap<String, Integer>();
		HashMap<String, Integer> ispanyolcaTriGramKelimeler = new HashMap<String, Integer>();

		for (String url : turkceSiteler)
		{
			kelimelerHesapla(turkceKelimeler, url);
		}
		for (String url : ingilizceSiteler)
		{
			kelimelerHesapla(ingilizceKelimeler, url);
		}
		for (String url : almacaSiteler)
		{
			kelimelerHesapla(almancaKelimeler, url);
		}
		for (String url : ispanyolcaSiteler)
		{
			kelimelerHesapla(ispanyolcaKelimeler, url);
		}

		System.out.println("turkce kelime" + turkceKelimeler.size());
		System.out.println("ing kelime" + ingilizceKelimeler.size());
		System.out.println("alm kelime" + almancaKelimeler.size());
		System.out.println("isp kelime" + ispanyolcaKelimeler.size());

		calculateTriGramFrequency(turkceTriGramKelimeler, turkceKelimeler);

		calculateTriGramFrequency(ingilizceTriGramKelimeler, ingilizceKelimeler);

		calculateTriGramFrequency(almancaTriGramKelimeler, almancaKelimeler);

		calculateTriGramFrequency(ispanyolcaTriGramKelimeler, ispanyolcaKelimeler);

		Scanner scanner = new Scanner(System.in);
		while (true)
		{
			System.out.println("Lutfen bir text giriniz...");

			String str = scanner.nextLine();
			String[] inputWords = splitText(str);
			String[] inputTrigrams = calculateTriGramFrequency(inputWords);

			float turkceBenzerlik = calculateJaccardSimilarity(turkceKelimeler, inputWords);
			float inglizceBenzerlik = calculateJaccardSimilarity(ingilizceKelimeler, inputWords);
			float almancaBenzerlik = calculateJaccardSimilarity(almancaKelimeler, inputWords);
			float ispanyolcaBenzerlik = calculateJaccardSimilarity(ispanyolcaKelimeler, inputWords);

			System.out.println("Turkce benzerlik = " + turkceBenzerlik);
			System.out.println("ing benzerlik = " + inglizceBenzerlik);
			System.out.println("alm benzerlik = " + almancaBenzerlik);
			System.out.println("isp benzerlik = " + ispanyolcaBenzerlik);

			ArrayList<Float> benzerlikler = new ArrayList<Float>();
			benzerlikler.add(turkceBenzerlik);
			benzerlikler.add(almancaBenzerlik);
			benzerlikler.add(inglizceBenzerlik);
			benzerlikler.add(ispanyolcaBenzerlik);
			System.out.println("===============================");
			for (Float float1 : benzerlikler)
			{
				System.out.println(float1);
			}
			System.out.println("===============================");
			Collections.sort(benzerlikler);

			for (Float float1 : benzerlikler)
			{
				System.out.println(float1);
			}

			if (benzerlikler.get(3) == turkceBenzerlik)
			{
				System.out.println("String Turkce'dir.");
			}
			if (benzerlikler.get(3) == inglizceBenzerlik)
			{
				System.out.println("String Ingilizce'dir.");
			}
			if (benzerlikler.get(3) == almancaBenzerlik)
			{
				System.out.println("String Almanca'dir.");
			}
			if (benzerlikler.get(3) == ispanyolcaBenzerlik)
			{
				System.out.println("String Ispanyolca'dir.");
			}

		}

	}

	public static String safeChar(String input)
	{
		char[] allowed = "abcdefghijklmnopqrstuvwxyz‰ﬂÒ„ı·ÈÌÛ˙‚ÍÙ‡ÂÁ˛˝¸".toCharArray();
		char[] charArray = input.toString().toCharArray();
		StringBuilder result = new StringBuilder();
		for (char c : charArray)
		{
			for (char a : allowed)
			{
				if (c == a)
					result.append(a);
			}
		}
		return result.toString();
	}

	public static void kelimelerHesapla(HashMap<String, Integer> map, String url)
	{
		Document doc = null;
		try
		{
			doc = Jsoup.connect(url).timeout(5000).get();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		if (doc != null)
		{
			Element contentDiv = doc.select("div[id=mw-content-text]").first();
			String text = contentDiv.getElementsByTag("div").text();

			text = text.toLowerCase();

			String[] kelimeler = splitText(text);
			calculateWordFrequency(map, kelimeler);
		}
		else
		{
			System.out.println("Wiki sayfalari okunamadi!\nLutfen internet baglantinizi kontrol ediniz.");
		}
	}

	public static String[] splitText(String str)
	{
		String[] s;
		s = str.split("\\s+");
		for (int i = 0; i < s.length; i++)
		{
			s[i] = safeChar(s[i]);
		}
		return s;
	}

	public static void calculateWordFrequency(HashMap<String, Integer> map, String[] words)
	{
		for (int i = 0; i < words.length; i++)
		{
			if ((words[i].length() >= 2) && map.containsKey(words[i]))
			{
				int freq = (int) map.get(words[i]) + 1;
				map.put(words[i], freq);
			}
			else
			{
				map.put(words[i], 1);
			}
		}
	}

	public static void calculateTriGramFrequency(HashMap<String, Integer> map, HashMap<String, Integer> kelimeler)
	{
		for (Map.Entry<String, Integer> entry : kelimeler.entrySet())
		{
			String key = entry.getKey();

			String str = "";
			str = "_" + key + "_";

			if (str.length() >= 2)
			{
				for (int k = 0; k < key.length(); k++)
				{
					String temp = "";
					temp += str.charAt(k);
					temp += str.charAt(k + 1);
					temp += str.charAt(k + 2);

					if (map.containsKey(temp))
					{
						int freq = (int) map.get(temp) + 1;
						map.put(temp, freq);
					}
					else
					{
						map.put(temp, 1);
					}
				}
			}
		}
	}

	public static String[] calculateTriGramFrequency(String kelimeler[])
	{
		ArrayList<String> triGrams = new ArrayList<String>();
		for (String s : kelimeler)
		{
			String str = "";
			str = "_" + s + "_";

			if (str.length() >= 2)
			{
				for (int k = 0; k < s.length(); k++)
				{
					String temp = "";
					temp += str.charAt(k);
					temp += str.charAt(k + 1);
					temp += str.charAt(k + 2);

					boolean varmi = false;
					for (String ss : triGrams)
					{
						if (ss.equals(temp))
						{
							varmi = true;
							break;
						}
					}
					if (!varmi)
					{
						triGrams.add(temp);
					}
				}
			}
		}
		String[] triWords = new String[triGrams.size()];
		triWords = triGrams.toArray(triWords);
		return triWords;
	}

	public static void displayMostFrequentWords(HashMap<String, Integer> map)
	{
		for (Map.Entry<String, Integer> entry : map.entrySet())
		{
			String key = entry.getKey();
			Integer value = entry.getValue();

			if (value >= 100)
			{
				System.out.println(key + " = " + value);
			}
		}
	}

	public static float calculateJaccardSimilarity(HashMap<String, Integer> map, String[] words)
	{
		float jaccardSimilarity = 0;

		int kesisim = 0, birlesim = 0;

		for (Map.Entry<String, Integer> entry : map.entrySet())
		{
			String s = entry.getKey();
			for (int i = 0; i < words.length; i++)
			{
				if (s.equals(words[i]))
				{
					kesisim++;
				}
			}
		}

		birlesim = map.size() + words.length - kesisim;
		jaccardSimilarity = (float) kesisim / birlesim;
		return jaccardSimilarity;
	}

}
