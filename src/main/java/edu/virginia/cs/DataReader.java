package edu.virginia.cs;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;

public class DataReader {
    List<ResearchOpportunity> researchList;
    String filename;
    private XSSFSheet sheet;

    public DataReader(String filename) {
        this.filename = filename;
        this.researchList = new ArrayList<ResearchOpportunity>();
    }
    public void readData() {
        try {
            generateXSSFSheet();
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

    protected void convertExcelToArrayList() {
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String title = "";
            if (row.getCell(2) != null) {
                title = row.getCell(2).getStringCellValue().strip();
                ResearchOpportunity ro = new ResearchOpportunity(title, null, null, "lol");
                researchList.add(ro);
            }
        }
    }

    protected void printResearchList() {
        for (int i = 0; i < researchList.size(); i++) {
            System.out.println(researchList.get(i).getTitle());
        }
    }

}
