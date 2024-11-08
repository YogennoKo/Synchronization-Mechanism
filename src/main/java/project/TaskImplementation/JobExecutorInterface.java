package project.TaskImplementation;

import project.JobGenerator.Job;

import java.util.List;
import java.util.Queue;

public interface JobExecutorInterface {
    void executeJobs(Queue<Job> jobQueue);
}
