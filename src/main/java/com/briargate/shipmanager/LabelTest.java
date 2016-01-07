package com.briargate.shipmanager;

import java.io.*;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;

import be.quodlibet.boxable.Row;

import com.google.common.base.Optional;
import com.google.common.io.Files;

public class LabelTest {
	public static void main(String[] args) throws IOException, COSVisitorException {
		String filename = args[0];
		File f = new File(filename);
		String outname = f.getName() + ".pdf";
		Optional<Packlist> packlist = Packlist.from(new FileInputStream(f));
		if (packlist.isPresent()) {
			//Initialize Document
	        PDDocument doc = new PDDocument();	           
	        ZebraTable table = createTable(doc, packlist.get());
	        table.draw();
	
	        //Close Stream and save pdf
	        File file = new File(outname);
	        System.out.println("Sample file saved at : " + file.getAbsolutePath());
	        Files.createParentDirs(file);
	        doc.save(file);
	        doc.close();
		}
    }

	private static ZebraTable createTable(PDDocument doc, Packlist packlist) throws IOException {
		ZebraTable table  = new ZebraTable(doc);
		for (String s : packlist.getHeaders()) {
			Row r = table.createRow(8f);
			table.createCell(r, 100f, s);
		}
		
		table.createRow(12f);
		for (PacklistItem item : packlist.getItems()) {
			Row r = table.createRow(10f);
			table.createCell(r, 8f, "[ ]");
			//table.createCell(r, 20f, item.getUpc());
			table.createCell(r, 80f, item.getTitle());
			table.createCell(r, 12f, item.getQuantity().toString());
		}
		return table;
	}
}
