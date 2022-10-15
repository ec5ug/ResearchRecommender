package edu.virginia.cs;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
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

    private int PROJECT_MANAGER_COLUMN_INDEX;
    private final int DURATION  = 1;

    public DataReader(String filename) {
        this.filename = filename;
        this.researchList = new ArrayList<ResearchOpportunity>();
        this.TITLE_COLUMN_INDEX = -1;
        this.SUMMARY_COLUMN_INDEX = -1;
        this.DATE_POSTED_COLUMN_INDEX = -1;
        this.PROJECT_MANAGER_COLUMN_INDEX = -1;
    }

    public void readData(HashMap<String, Boolean> data) {
        try {
            generateXSSFSheet();
            this.TITLE_COLUMN_INDEX = colInd("Project Title");
            this.SUMMARY_COLUMN_INDEX = colInd("Short Description of Work");
            this.DATE_POSTED_COLUMN_INDEX = colInd("Date");
            this.PROJECT_MANAGER_COLUMN_INDEX = colInd("Name");
            convertExcelToArrayList(data);
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
            if (row.getCell(i).getStringCellValue().strip().contains(colName))
                return i;
        }
        return -1;
    }

    protected void convertExcelToArrayList(HashMap<String, Boolean> data) {
        HashSet<String> dontWant = new HashSet<String>();
        for (String key: data.keySet()) {
            if(!data.get(key))
                dontWant.add(key);
        }
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
                String name = row.getCell(PROJECT_MANAGER_COLUMN_INDEX).getStringCellValue().strip();
                if (!title.equals("") && sDate.matches(".*[0-9].*")) {
                    String[] arr = sDate.split("/");
                    arr[2] = "20" + arr[2];
                    LocalDate date = LocalDate.of(Integer.parseInt(arr[2]), Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));

                    name = name.split(",")[0];
                    name = name.split("\\(")[0];
                    name = name.split("and")[0];
                    name = name.split("&")[0];

                    if (Period.between(date, LocalDate.now()).getYears() < DURATION) {
                        ResearchOpportunity ro = new ResearchOpportunity(title, new ProjectManager(name, ""), date, summary);
                        if (Collections.disjoint(ro.getType(), dontWant))
                            researchList.add(ro);
                    }
                }
            }
        }
    }

    protected void printResearchList() {
        for (int i = 0; i < researchList.size(); i++) {
            System.out.println(researchList.get(i).getTitle() + " | Project Manager: " +
                    researchList.get(i).getProjectManager().getName());
            System.out.println("Tag List: " + researchList.get(i).getType());
            System.out.println("Date Posted: " + researchList.get(i).getDate()); // DELETE WHEN DONE
            System.out.println(researchList.get(i).getSummary());
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

}
