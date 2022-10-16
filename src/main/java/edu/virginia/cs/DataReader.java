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
    private int EMAIL_COLUMN_INDEX;
    private final int DURATION  = 2;

    public DataReader(String filename) {
        this.filename = filename;
        this.researchList = new ArrayList<ResearchOpportunity>();
        this.TITLE_COLUMN_INDEX = -1;
        this.SUMMARY_COLUMN_INDEX = -1;
        this.DATE_POSTED_COLUMN_INDEX = -1;
        this.PROJECT_MANAGER_COLUMN_INDEX = -1;
        this.EMAIL_COLUMN_INDEX = -1;
    }

    public void readData(HashMap<String, Boolean> data) {
        try {
            generateXSSFSheet();
            resetIndices();
            convertExcelToArrayList(data);
            printResearchList();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void resetIndices() {
        this.TITLE_COLUMN_INDEX = colInd("Project Title");
        this.SUMMARY_COLUMN_INDEX = colInd("Short Description of Work");
        this.DATE_POSTED_COLUMN_INDEX = colInd("Date");
        this.PROJECT_MANAGER_COLUMN_INDEX = colInd("Name");
        this.EMAIL_COLUMN_INDEX = colInd("Link");
        if (this.EMAIL_COLUMN_INDEX < 0)
            this.EMAIL_COLUMN_INDEX = colInd("Email");
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
        HashSet<String> dontWant = extractFlaggedData(data);
        Iterator<Row> rowIterator = sheet.iterator();
        DataFormatter dataFormatter = new DataFormatter();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (hasAProjectTitle(row)) {
                String title = getTitle(row);
                String sDate = getRawDate(dataFormatter, row);
                String name = getRawName(row);
                String email = "not present (see UVA Internal Peoples Search)";
                if (hasAnEmail(row))
                    email = getRawEmail(row);
                if (validTitleAndDate(title, sDate)) {
                    LocalDate date = cleanLocaleDate(sDate);
                    name = cleanNameOfProjectManager(name);
                    email = cleanEmailOfProjectManager(email);
                    if (isResearchRecent(date)) {
                        ResearchOpportunity ro = new ResearchOpportunity(title, new ProjectManager(name, email), date, getSummary(row));
                        if (Collections.disjoint(ro.getType(), dontWant))
                            researchList.add(ro);
                    }
                }
            }
        }
    }

    private boolean validTitleAndDate(String title, String sDate) {
        return !title.equals("") && sDate.matches(".*[0-9].*");
    }

    private boolean hasAnEmail(Row row) {
        return row.getCell(EMAIL_COLUMN_INDEX) != null;
    }

    private boolean hasAProjectTitle(Row row) {
        return row.getCell(TITLE_COLUMN_INDEX) != null;
    }

    private HashSet<String> extractFlaggedData(HashMap<String, Boolean> data) {
        HashSet<String> dontWant = new HashSet<String>();
        for (String key: data.keySet()) {
            if(!data.get(key))
                dontWant.add(key);
        }
        return dontWant;
    }

    private String getRawName(Row row) {
        return row.getCell(PROJECT_MANAGER_COLUMN_INDEX).getStringCellValue().strip();
    }

    private String getRawDate(DataFormatter dataFormatter, Row row) {
        return dataFormatter.formatCellValue(row.getCell(DATE_POSTED_COLUMN_INDEX));
    }

    private String getRawEmail(Row row) {
        return row.getCell(EMAIL_COLUMN_INDEX).getStringCellValue().strip();
    }

    private String getSummary(Row row) {
        return row.getCell(SUMMARY_COLUMN_INDEX).getStringCellValue().strip();
    }

    private String getTitle(Row row) {
        return row.getCell(TITLE_COLUMN_INDEX).getStringCellValue().strip();
    }

    private boolean isResearchRecent(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears() < DURATION;
    }

    private LocalDate cleanLocaleDate(String sDate) {
        String[] arr = sDate.split("/");
        arr[2] = "20" + arr[2];
        LocalDate date = LocalDate.of(Integer.parseInt(arr[2]), Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
        return date;
    }

    private String cleanNameOfProjectManager(String name) {
        name = name.split(",")[0];
        name = name.split("\\(")[0];
        name = name.split("and")[0];
        name = name.split("&")[0];
        return name;
    }

    private String cleanEmailOfProjectManager(String email) {
        email = email.split(",")[0];
        return email;
    }

    protected void printResearchList() {
        for (int i = 0; i < researchList.size(); i++) {
            System.out.println(researchList.get(i).getTitle());
            System.out.println("Project Manager: " + researchList.get(i).getProjectManager().getName() +
                    " | For more info: " + researchList.get(i).getProjectManager().getEmail());
            System.out.println("Tag List: " + researchList.get(i).getType());
            System.out.println("Date Posted: " + researchList.get(i).getDate()); // DELETE WHEN DONE
            System.out.println(researchList.get(i).getSummary());
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

}
