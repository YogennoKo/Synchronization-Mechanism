package project.TaskImplementation;

import project.JobGenerator.Job;
import project.Utils.Constants;
import project.Utils.Logger;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JobExecutorMutex implements JobExecutorInterface {

    private final Lock printerLock = new ReentrantLock();
    private final Lock scannerLock = new ReentrantLock();
    private static final AtomicInteger totalProcessedPages = new AtomicInteger(0);

    @Override
    public void executeJobs(Queue<Job> jobQueue) {
        while (!jobQueue.isEmpty()) {
            Job job = jobQueue.poll();
            if (job != null) {
                new Thread(() -> {
                    try {
                        Thread.sleep(job.getArrivalTime());
                        executeJob(job);
                    } catch (InterruptedException e) {
                        Logger.log("Job interrupted: " + job);
                    }
                }).start();
            }
        }
    }

    private void executeJob(Job job) {
        Logger.log("Starting " + job);

        if (job.getJobType() == Job.JobType.PRINT) {
            printerLock.lock();
            try {
                processJob(job);
            } finally {
                printerLock.unlock();
            }
        } else if (job.getJobType() == Job.JobType.SCAN) {
            scannerLock.lock();
            try {
                processJob(job);
            } finally {
                scannerLock.unlock();
            }
        }
    }

    private void processJob(Job job) {
        for (int i = 0; i < job.getPages(); i++) {
            try {
                Thread.sleep(Constants.PAGE_PROCESSING_TIME);
                int currentPage = totalProcessedPages.incrementAndGet();
                Logger.log("Processed page " + (i + 1) + " of " + job + " | Total Processed Pages: " + currentPage);
            } catch (InterruptedException e) {
                Logger.log("Job execution interrupted: " + job);
            }
        }
        Logger.log("Completed " + job);
    }
}
