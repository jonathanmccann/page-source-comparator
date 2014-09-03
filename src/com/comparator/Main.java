package com.comparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class Main {
	public static void main (String[] args) throws IOException {
		for (String input : args) {
			String urlSource = getUrlSource(input);

			PrintWriter writer = new PrintWriter(
				"original-page-source.html", "UTF-8");

			writer.println(urlSource);

			writer.close();
		}
	}

	private static String getUrlSource(String url) throws IOException {
		URL sourceUrl = new URL(url);

		URLConnection sourceUrlConnection = sourceUrl.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(
			sourceUrlConnection.getInputStream(), "UTF-8"));

		String inputLine = null;

		StringBuilder a = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			a.append(inputLine);
		}

		in.close();

		return a.toString();
	}

}