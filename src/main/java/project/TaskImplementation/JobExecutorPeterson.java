package project.TaskImplementation;

import project.JobGenerator.Job;
import project.Utils.Constants;
import project.Utils.Logger;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class JobExecutorPeterson implements JobExecutorInterface {

    private final Object scannerLock = new Object();
    private static final AtomicInteger totalProcessedPages = new AtomicInteger(0);

    private volatile boolean[] flag = new boolean[2];
    private volatile int turn;

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
            int id = Character.getNumericValue(job.getUser().charAt(1)) % 2;
            int otherId = 1 - id;

            flag[id] = true;
            turn = otherId;
            while (flag[otherId] && turn == otherId) {
            }
            processJob(job);
            flag[id] = false;
        } else if (job.getJobType() == Job.JobType.SCAN) {
            synchronized (scannerLock) {
                processJob(job);
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
