package limeng32.testSpring.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class UserListPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("用户列表".getBytes(), "iso8859-1") + ".pdf");
		Table table = new Table(3);
		table.setWidth(120);
		table.setBorder(1);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BASELINE);

		// BaseFont cnBaseFont = BaseFont.createFont("STSongStd-Light",
		// "UniGB-UCS2-H", true);

		// Font cnFont = new Font(cnBaseFont, 10, Font.NORMAL, Color.BLUE);

		table.addCell(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
		table.addCell("qwe");
		table.addCell("zxc");
		table.addCell("fgh");

		document.add(table);
	}

}
