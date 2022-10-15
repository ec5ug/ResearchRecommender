package edu.virginia.cs;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;

public class DataReader {
    List<ResearchOpportunity> researchList;
    String filename;
    private XSSFSheet sheet;

    private int TITLE_COLUMN_INDEX;
    private int SUMMARY_COLUMN_INDEX;
    private int DATE_POSTED_COLUMN_INDEX;

    public DataReader(String filename) {
        this.filename = filename;
        this.researchList = new ArrayList<ResearchOpportunity>();
        this.TITLE_COLUMN_INDEX = -1;
        this.SUMMARY_COLUMN_INDEX = -1;
        this.DATE_POSTED_COLUMN_INDEX = -1;
    }
    public void readData() {
        try {
            generateXSSFSheet();
            this.TITLE_COLUMN_INDEX = colInd("Project Title");
            this.SUMMARY_COLUMN_INDEX = colInd("Short Description of Work");
            this.DATE_POSTED_COLUMN_INDEX = colInd("Date Posted");
            convertExcelToArrayList();
            printResearchList();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    protected void generateXSSFSheet() throws IOException {
        FileInputStream file = new FileInputStream(new File(this.filename));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
    }

    protected int colInd(String colName) {
        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();
        for (int i = 0; i < row.getLastCellNum(); i ++) {
            if (row.getCell(i).getStringCellValue().strip().equals(colName))
                    return i;
        }
        return -1;
    }

    protected void convertExcelToArrayList() {
        Iterator<Row> rowIterator = sheet.iterator();
        DataFormatter dataFormatter = new DataFormatter();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String title = "";
            String summary = "";
            if (row.getCell(TITLE_COLUMN_INDEX) != null) {
                title = row.getCell(TITLE_COLUMN_INDEX).getStringCellValue().strip();
                summary = row.getCell(SUMMARY_COLUMN_INDEX).getStringCellValue().strip();
                String sDate = dataFormatter.formatCellValue(row.getCell(DATE_POSTED_COLUMN_INDEX));
                if (!title.equals("") && sDate.matches(".*[0-9].*")) {
                    String[] arr = sDate.split("/");
                    arr[2] = "20" + arr[2];
                    LocalDate date = LocalDate.of(Integer.parseInt(arr[2]), Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                    System.out.println(date);
                    ResearchOpportunity ro = new ResearchOpportunity(title, null, date, summary);
                    researchList.add(ro);
                }
            }
        }
    }

    protected void printResearchList() {
        for (int i = 0; i < researchList.size(); i++) {
            System.out.println(researchList.get(i).getTitle() + "| Date Posted: " + researchList.get(i).getDate());
            System.out.println(researchList.get(i).getSummary());
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

}
