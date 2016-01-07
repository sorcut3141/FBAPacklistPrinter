package com.briargate.shipmanager;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import be.quodlibet.boxable.*;

public class ZebraTable extends BaseTable {
	public static final int LABEL_XSIZE = 288;
	public static final int LABEL_YSIZE = 432;
	
	private static final float margin = 12;
	private static final float bottomMargin = 12;
	private static final float yStartNewPage = LABEL_YSIZE - margin;
	private static final float tableWidth = LABEL_XSIZE - (2 * margin);
	
	public ZebraTable(PDDocument document) throws IOException {
		super(yStartNewPage, yStartNewPage, bottomMargin, tableWidth, margin, document, getPage(document), false, true);
	}

	@Override
	protected PDPage createPage() {
		return getPage(getDocument());
	}
	
	public Cell createCell(Row row, float widthPct, String text) {
		Cell cell = row.createCell(widthPct, text);
        cell.setFont(PDType1Font.COURIER_BOLD);
        cell.setFontSize(8);
        return cell;
	}
	
	private static PDPage getPage(PDDocument doc) {
		PDPage page = new PDPage(new PDRectangle(LABEL_XSIZE, LABEL_YSIZE));
        doc.addPage(page);
		return page;
	}
}
