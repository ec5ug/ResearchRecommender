package edu.virginia.cs;

public class ProjectManager {
    String name;
    String email;

    public ProjectManager(String name, String email) {
        this.name = name;
        this.email = email;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected String getName() {return this.name;}
    protected String getEmail() {return this.email;}

}
