package com.briargate.shipmanager;

import java.io.*;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import au.com.bytecode.opencsv.CSVReader;
import static com.briargate.shipmanager.PacklistColumns.*;

public class Packlist {
	private List<String> headers;
	private List<PacklistItem> items;
	
	public Packlist() {
		headers = Lists.newArrayList();
		items = Lists.newArrayList();
	}
	
	public List<String> getHeaders() {
		return headers;
	}
	
	public List<PacklistItem> getItems() {
		return items;
	}
	
	public static Optional<Packlist> from(InputStream inputStream) throws IOException {
		CSVReader rdr = new CSVReader(new InputStreamReader(inputStream), '\t', '~');
		List<String[]> lines = rdr.readAll();
		rdr.close();
		
		if (lines.size() >= 9) {
			Packlist packlist = new Packlist();
			lines = processHeader(lines, packlist);
			processContents(lines, packlist);
			return Optional.of(packlist);
		} else {
			return Optional.absent();
		}
	}

	private static void processContents(List<String[]> lines, Packlist packlist) {
		lines.remove(0);
		for (String[] curLine : lines) {
			System.out.println(StringUtils.join(curLine, "|"));
			String upc = parseUPC(curLine[BARCODE.position()]);
			PacklistItem item = new PacklistItem(upc, curLine[TITLE.position()], curLine[QUANTITY.position()]);
			packlist.addItem(item);
		}
	}

	private static String parseUPC(String input) {
		if (StringUtils.startsWith(input, "EAN")) {
			return StringUtils.substring(input, "EAN : ".length());
		} else {
			return input;
		}
	}

	private void addItem(PacklistItem item) {
		this.items.add(item);
	}

	private static List<String[]> processHeader(List<String[]> lines, Packlist packlist) {
		for (int i=0; i < 7; i++) {
			String[] nextLine = lines.get(i);
			System.out.println(StringUtils.join(nextLine, " "));
			packlist.addHeaderLine(nextLine);
		}
		
		return lines.subList(8, lines.size());
	}

	private void addHeaderLine(String[] nextLine) {
		this.headers.add(StringUtils.join(nextLine, ","));
	}
}
