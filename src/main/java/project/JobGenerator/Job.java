package project.JobGenerator;

import lombok.Getter;

public class Job {
    public enum JobType { PRINT, SCAN }

    @Getter private final String user;
    @Getter private final JobType jobType;
    @Getter private final int pages;
    @Getter private final long arrivalTime;

    public Job(String user, JobType jobType, int pages, long arrivalTime) {
        this.user = user;
        this.jobType = jobType;
        this.pages = pages;
        this.arrivalTime = arrivalTime;
    }


    @Override
    public String toString() {
        return "User " + user + ": " + jobType + " Job, " + pages + " pages, Arrival Time: " + arrivalTime/1000 + "s";
    }
}

