package project.JobGenerator;


import project.Utils.Constants;

import java.util.*;

public class JobGenerator {
    private static final Random random = new Random();

    public static Queue<Job> generateJobsForUser(String userId) {
        Queue<Job> jobs = new LinkedList<>();
        long currentTime = 0;

        for (int i = 0; i < 10; i++) {
            Job.JobType jobType = random.nextBoolean() ? Job.JobType.PRINT : Job.JobType.SCAN;
            int pages = determinePages();
            currentTime += 1000 * (1 + random.nextInt(5));
            jobs.add(new Job(userId, jobType, pages, currentTime));
        }

        return jobs;
    }

    private static int determinePages() {
        int jobType = random.nextInt(3);
        return switch (jobType) {
            case 0 -> 1 + random.nextInt(Constants.SHORT_JOB_MAX_PAGES);
            case 1 -> 6 + random.nextInt(Constants.MEDIUM_JOB_MAX_PAGES - 6 + 1);
            default -> 16 + random.nextInt(Constants.LARGE_JOB_MAX_PAGES - 16 + 1);
        };
    }
}

