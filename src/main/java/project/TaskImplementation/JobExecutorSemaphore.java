package project.TaskImplementation;

import project.JobGenerator.Job;
import project.Utils.Constants;
import project.Utils.Logger;

import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class JobExecutorSemaphore implements JobExecutorInterface {

    private final Semaphore printerSemaphore = new Semaphore(1);
    private final Semaphore scannerSemaphore = new Semaphore(1);
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

        try {
            if (job.getJobType() == Job.JobType.PRINT) {
                printerSemaphore.acquire();
                processJob(job);
            } else if (job.getJobType() == Job.JobType.SCAN) {
                scannerSemaphore.acquire();
                processJob(job);
            }
        } catch (InterruptedException e) {
            Logger.log("Job execution interrupted: " + job);
        } finally {
            if (job.getJobType() == Job.JobType.PRINT) {
                printerSemaphore.release();
            } else {
                scannerSemaphore.release();
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
