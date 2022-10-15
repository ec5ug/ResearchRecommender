package edu.virginia.cs;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ResearchOpportunity {

    protected String title;
    protected Set<String> type;
    protected ProjectManager projectManager;
    protected boolean availability;
    protected LocalDate date;
    protected String summary;

    protected final int cutOffDate = 5;

    public ResearchOpportunity(String title, ProjectManager projectManager, LocalDate date, String summary) {
        this.title = title;
        this.type = determineType(summary);
        this.projectManager = projectManager;
        this.availability = true;
        this.date = date;
        this.summary = summary;
    }

    public Set<String> determineType(String summary) {
        String temp = summary.toLowerCase();
        Set<String> tempSet = new HashSet<String>();

        HashMap <String,String> dict = new HashMap<String,String>();
        try {
            FileInputStream file = new FileInputStream(new File("ForHashMap.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                dict.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
            }
        } catch (IOException e) {}

        for (String key : dict.keySet()) {
            if (temp.contains(key)) {
                tempSet.add(dict.get(key));
            }
        }
        return tempSet;
    }

    public String getTitle() {return this.title;}
    public String getSummary() {return this.summary;};

    public String getDate() {return this.date.toString();};

    public ProjectManager getProjectManager() {return this.projectManager;}

    public Set<String> getType() {return this.type;}

    protected void editSummary(String newSummary) {
        this.summary = newSummary;
    }

    protected void setUnavailability() {
        this.availability = false;
    }

    protected void setAvailability() {
        this.availability = true;
    }

}
