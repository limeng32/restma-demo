package limeng32.testSpring.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class UserListExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String("用户列表".getBytes(), "iso8859-1") + ".xls");
		HSSFSheet sheet = workbook.createSheet("users");
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("中文0");
		header.createCell(1).setCellValue("z");
		header.createCell(2).setCellValue("中文2");
	}
}
