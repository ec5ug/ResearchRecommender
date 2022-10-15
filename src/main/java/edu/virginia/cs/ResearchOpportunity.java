package edu.virginia.cs;

import java.util.*;

public class ResearchOpportunity {
    protected Set<ResearchType> type;
    protected ProjectManager projectManager;
    protected boolean availability;
    protected Date date;
    protected String summary;

    public ResearchOpportunity(ProjectManager projectManager, Date date, String summary) {
        this.type = determineType(summary);
        this.projectManager = projectManager;
        this.availability = true;
        this.date = date;
        this.summary = summary;
    }

    public Set<ResearchType> determineType(String summary) {
        return null;
    }

    private void editSummary(String newSummary) {
        this.summary = newSummary;
    }

    private void setUnavailability() {
        this.availability = false;
    }

    private void setAvailability() {
        this.availability = true;
    }
}
