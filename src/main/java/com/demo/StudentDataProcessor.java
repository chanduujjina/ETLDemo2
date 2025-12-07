package com.demo;

import java.io.FileInputStream;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.demo.dto.CCStudentDetails;

public class StudentDataProcessor {

	public static void main(String[] args) {

		Pipeline p = Pipeline.create();

		p.apply("Excel input Path", org.apache.beam.sdk.transforms.Create.of("E://Chandu//CC_Student_info.xlsx"))
				.apply("Read Excel", ParDo.of(new ReadExcelFn()))
				.apply("WriteToDB", saveToDatabase());

		p.run().waitUntilFinish();

	}

	public static JdbcIO.Write<CCStudentDetails> saveToDatabase() {
		return JdbcIO.<CCStudentDetails>write()
				.withDataSourceConfiguration(JdbcIO.DataSourceConfiguration
						.create("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/etl_db").withUsername("root")
						.withPassword("root"))
				.withStatement(
						"INSERT INTO cc_student_info (std_id, std_name, gender,phoneNumber,skillset,total_experince) VALUES (?, ?, ?,?,?,?)")
				.withPreparedStatementSetter((studentInfo, ps) -> {
					ps.setInt(1, studentInfo.getId());
					ps.setString(2, studentInfo.getName());
					ps.setString(3, studentInfo.getGender());
					ps.setString(4, studentInfo.getPhoneNumber());
					ps.setString(5, studentInfo.getSkillSet());
					ps.setInt(6, studentInfo.getTotalExperince());
				});

	}

	public static class ReadExcelFn extends DoFn<String, CCStudentDetails> {
		@ProcessElement
		public void processElement(ProcessContext c) throws Exception {
			String excelPath = c.element();//reading data from pipeline
			FileInputStream fis = new FileInputStream(excelPath);
			Workbook workbook = new XSSFWorkbook(fis);//excel
			Sheet sheet = workbook.getSheetAt(0);//sheet no

			boolean skipHeader = true;
			for (Row row : sheet) {
				if (skipHeader) {
					skipHeader = false;
					continue;
				}

				int id = (int) row.getCell(0).getNumericCellValue();
				String name = row.getCell(1).getStringCellValue();
				String gender = row.getCell(2).getStringCellValue();
				int experience = (int) row.getCell(3).getNumericCellValue();
				Double phoneNumber = row.getCell(4).getNumericCellValue();
				String skillSet = row.getCell(5).getStringCellValue();

				c.output(new CCStudentDetails(id, name, gender, String.valueOf(phoneNumber), experience, skillSet));//write data to pipeline
			}

			workbook.close();
			fis.close();
		}
	}

}
