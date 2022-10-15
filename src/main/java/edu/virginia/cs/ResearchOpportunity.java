package edu.virginia.cs;

import java.time.LocalDate;
import java.util.*;

public class ResearchOpportunity {

    protected String title;
    protected Set<ResearchType> type;
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

    public Set<ResearchType> determineType(String summary) {
        return null;
    }

    public String getTitle() {return this.title;}
    public String getSummary() {return this.summary;};

    public String getDate() {return this.date.toString();};
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
