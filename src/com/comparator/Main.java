package com.comparator;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main (String arg) throws IOException {
		String pageSource = getPageSource(arg);

		PrintWriter writer = new PrintWriter(
			_CURRENT_PAGE_SOURCE, "UTF-8");

		writer.println(pageSource);

		writer.close();

		printDiff(_PREVIOUS_PAGE_SOURCE, _CURRENT_PAGE_SOURCE);

		new File(_PREVIOUS_PAGE_SOURCE).delete();

		new File(_CURRENT_PAGE_SOURCE).renameTo(
			new File(_PREVIOUS_PAGE_SOURCE));
	}

	private static String getPageSource(String url) throws IOException {
		URL sourceUrl = new URL(url);

		URLConnection sourceUrlConnection = sourceUrl.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(
			sourceUrlConnection.getInputStream(), "UTF-8"));

		String inputLine = null;

		StringBuilder sb = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			sb.append(inputLine);
			sb.append('\n');
		}

		in.close();

		return sb.toString();
	}

	private static List<String> getPageSourceLines(String sourceFileName)
		throws IOException{

		List<String> lines = new ArrayList<String>();

		String line = null;

		BufferedReader in = new BufferedReader(new FileReader(sourceFileName));

		while ((line = in.readLine()) != null) {
			lines.add(line);
		}

		return lines;
	}

	private static void printDiff(
			String previousSourceFileName, String currentSourceFileName)
		throws IOException{

		List<String> previousSource = getPageSourceLines(previousSourceFileName);
		List<String> currentSource = getPageSourceLines(currentSourceFileName);

		Patch patch = DiffUtils.diff(previousSource, currentSource);

		if (patch.getDeltas().size() == 0) {
			System.out.println("The two pages are identical");
		}
		else {
			for (Delta delta : patch.getDeltas()) {
				System.out.println(delta);
			}
		}
	}

	private static final String _CURRENT_PAGE_SOURCE =
		"current-page-source.html";
	private static final String _PREVIOUS_PAGE_SOURCE =
		"previous-page-source.html";

}