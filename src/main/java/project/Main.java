package project;

import project.JobGenerator.Job;
import project.JobGenerator.JobGenerator;
import project.TaskImplementation.JobExecutorMutex;
import project.TaskImplementation.JobExecutorNoSync;
import project.TaskImplementation.JobExecutorPeterson;
import project.TaskImplementation.JobExecutorSemaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        List<Queue<Job>> jobslist = new ArrayList<>();
        jobslist.add(JobGenerator.generateJobsForUser("P1"));
        jobslist.add(JobGenerator.generateJobsForUser("P2"));
        jobslist.add(JobGenerator.generateJobsForUser("P3"));
        jobslist.add(JobGenerator.generateJobsForUser("P4"));
        jobslist.add(JobGenerator.generateJobsForUser("P5"));

        JobExecutorNoSync executorNoSync = new JobExecutorNoSync();
        JobExecutorSemaphore executorSemaphore = new JobExecutorSemaphore();
        JobExecutorMutex executorMutex = new JobExecutorMutex();
        JobExecutorPeterson executorPeterson = new JobExecutorPeterson();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int userIndex = i;
            Thread userThread = new Thread(() -> {
                executorPeterson.executeJobs(jobslist.get(userIndex));
            });
            userThread.setName("UserThread-P" + (i + 1));
            threads.add(userThread);
            userThread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted: " + thread.getName());
            }
        }

    }
}