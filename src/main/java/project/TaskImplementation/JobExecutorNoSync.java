package project.TaskImplementation;

import project.JobGenerator.Job;
import project.Utils.Constants;
import project.Utils.Logger;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class JobExecutorNoSync implements JobExecutorInterface {

    private static final AtomicInteger totalProcessedPages = new AtomicInteger(0);

    @Override
    public void executeJobs(Queue<Job> jobQueue) {
        while (!jobQueue.isEmpty()) {
            Job job = jobQueue.poll();
            if (job != null) {
                new Thread(() -> executeJob(job)).start();
            }
        }
    }

    private void executeJob(Job job) {
        Logger.log("Starting " + job);

        if (job.getJobType() == Job.JobType.PRINT) {
            processJob(job);
        } else if (job.getJobType() == Job.JobType.SCAN) {
            processJob(job);
        }
    }

    private void processJob(Job job) {
        for (int i = 0; i < job.getPages(); i++) {
            try {
                Thread.sleep(Constants.PAGE_PROCESSING_TIME);
                int currentPage = totalProcessedPages.incrementAndGet();

                Logger.log("Total pages " + totalProcessedPages + "; Processed page " + (i + 1) + " of " + job +
                           " | Total Processed Pages: " + currentPage);

            } catch (InterruptedException e) {
                Logger.log("Job execution interrupted: " + job);
            }
        }
        Logger.log("Completed " + job);
    }
}
