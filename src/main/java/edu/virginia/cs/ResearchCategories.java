package edu.virginia.cs;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ResearchCategories {
    protected HashMap<String, Boolean> categories;

    public ResearchCategories() {
        this.categories = findUniqueCategories();
    }

    protected HashMap<String, Boolean> findUniqueCategories() {
        HashMap<String, Boolean> tempSet = new HashMap<String, Boolean>();
        try {
            FileInputStream file = new FileInputStream(new File("ForHashMap.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                tempSet.put(row.getCell(1).getStringCellValue(), true);
            }
        } catch (IOException e) {}
        return tempSet;
    }

}
