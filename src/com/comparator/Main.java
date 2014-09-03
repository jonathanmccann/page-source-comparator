package com.comparator;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main (String arg) throws IOException {
		List<String> pageSource = getPageSource(arg);

		PrintWriter writer = new PrintWriter(
			"original-page-source.html", "UTF-8");

		writer.println(pageSource);

		writer.close();
	}

	private static List<String> getPageSource(String url) throws IOException {
		URL sourceUrl = new URL(url);

		URLConnection sourceUrlConnection = sourceUrl.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(
			sourceUrlConnection.getInputStream(), "UTF-8"));

		String inputLine = null;

		List<String> pageSource = new ArrayList<String>();

		while ((inputLine = in.readLine()) != null) {
			pageSource.add(inputLine);
		}

		in.close();

		return pageSource;
	}

	private static void printDiff(
		List<String> previousSource, List<String> currentSource) {

		Patch patch = DiffUtils.diff(previousSource, currentSource);

		for (Delta delta : patch.getDeltas()) {
			System.out.println(delta);
		}
	}

}