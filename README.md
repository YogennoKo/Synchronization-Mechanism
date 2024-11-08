# Shared Resource Synchronization Assignment

## Purpose

This project implements and compares process synchronization mechanisms to prevent race conditions, deadlocks, and
incorrect outputs in a shared office environment. Five users (P1 to P5) access shared resources (printer and scanner) to
complete jobs, each consisting of print and scan tasks with varied page lengths. The objective is to demonstrate the
effects of synchronization and compare different mechanisms.

---

## Table of Contents

1. [Overview](#overview)
2. [Part 1: Job Generator](#part-1-job-generator)
3. [Part 2: Task Implementation](#part-2-task-implementation)
    - [Task 1: Without Synchronization](#task-1-without-synchronization)
    - [Task 2: With Synchronization](#task-2-with-synchronization)
4. [Execution Results](#execution-results)
    - [Without Synchronization](#without-synchronization)
    - [With Semaphore Synchronization](#with-semaphore-synchronization)
    - [With Mutex Synchronization](#with-mutex-synchronization)
    - [With Peterson Synchronization](#with-petersons-solution-synchronization)
---

## Overview

This project simulates a shared office environment where multiple users perform print and scan jobs on shared resources.
Synchronization mechanisms are implemented to explore and address issues such as race conditions and deadlocks.

---

## Part 1: Job Generator

The Job Generator creates 10 random jobs for each user, with the following features:

- **Job Types**: Each job is randomly assigned as either a print or scan job.
- **Job Lengths**:
    - Short Job: 1–5 pages
    - Medium Job: 6–15 pages
    - Large Job: 16–50 pages
- **Arrival Times**: Random arrival times simulate varying intervals (between 1 and 5 seconds).
- **Output Format**:
    - Each job includes details like the user, job type, length, and arrival time.

---

## Part 2: Task Implementation

### Task 1: Without Synchronization

**Objective**  
This task executes generated jobs without any synchronization. Users can access resources (printer and scanner)
simultaneously, leading to potential race conditions.

**Execution Constraints**

- Jobs may be pre-empted after each page.
- No job interrupts another mid-page.

**Expected Issues**

- **Race Conditions**: Multiple users access resources simultaneously.
- **Deadlocks**: Resource conflicts may occur if users wait indefinitely.

**Output**  
Logs show each job’s page-by-page status, including errors or inconsistencies from concurrent access.

### Task 2: With Synchronization

In the synchronized version, jobs wait for exclusive access to resources (printer/scanner) to prevent interference.

**Synchronization Mechanisms**

1. **Mutexes**: Exclusive access for preventing resource conflicts.
2. **Semaphores**: Controlled access for prioritizing jobs.
3. **Peterson’s Solution**: Ensures mutual exclusion with a focus on two processes.

**Execution Constraints**

- Jobs switch only at page boundaries.
- Only one job accesses a resource at any time.

---

### Without Synchronization

**Issues**

- Race conditions between jobs using the same resource simultaneously - **they almost always happen**.
- Deadlocks - **the deadline could not be reached because all operations have an end and it is impossible to lock the
  resource for an infinite amount of time**.

Example of results:

```
Total pages 47; Processed page 1 of User P2: PRINT Job, 24 pages, Arrival Time: 6s | Total Processed Pages: 47
Total pages 32; Processed page 1 of User P2: PRINT Job, 3 pages, Arrival Time: 12s | Total Processed Pages: 32
Total pages 35; Processed page 1 of User P3: SCAN Job, 44 pages, Arrival Time: 15s | Total Processed Pages: 35
Total pages 50; Processed page 1 of User P2: SCAN Job, 8 pages, Arrival Time: 35s | Total Processed Pages: 50
Total pages 16; Processed page 1 of User P1: PRINT Job, 16 pages, Arrival Time: 24s | Total Processed Pages: 16
Total pages 13; Processed page 1 of User P1: PRINT Job, 1 pages, Arrival Time: 22s | Total Processed Pages: 13

```

**According to the total number of processed pages, it is clear that a race condition occurs**

### With Semaphore Synchronization

```
Starting User P2: PRINT Job, 2 pages, Arrival Time: 1s
Starting User P3: SCAN Job, 22 pages, Arrival Time: 1s
Starting User P1: SCAN Job, 14 pages, Arrival Time: 2s
Starting User P4: SCAN Job, 7 pages, Arrival Time: 2s
Processed page 1 of User P3: SCAN Job, 22 pages, Arrival Time: 1s | Total Processed Pages: 1
Processed page 1 of User P2: PRINT Job, 2 pages, Arrival Time: 1s | Total Processed Pages: 2
Processed page 2 of User P2: PRINT Job, 2 pages, Arrival Time: 1s | Total Processed Pages: 3
Completed User P2: PRINT Job, 2 pages, Arrival Time: 1s
Processed page 2 of User P3: SCAN Job, 22 pages, Arrival Time: 1s | Total Processed Pages: 4
Starting User P2: PRINT Job, 11 pages, Arrival Time: 4s
Processed page 3 of User P3: SCAN Job, 22 pages, Arrival Time: 1s | Total Processed Pages: 5

```

### With Mutex Synchronization

```
Starting User P2: SCAN Job, 46 pages, Arrival Time: 1s
Processed page 1 of User P2: SCAN Job, 46 pages, Arrival Time: 1s | Total Processed Pages: 1
Starting User P1: PRINT Job, 12 pages, Arrival Time: 3s
Starting User P5: PRINT Job, 4 pages, Arrival Time: 3s
Processed page 2 of User P2: SCAN Job, 46 pages, Arrival Time: 1s | Total Processed Pages: 2
Starting User P2: SCAN Job, 1 pages, Arrival Time: 4s
Starting User P5: PRINT Job, 15 pages, Arrival Time: 4s
Processed page 1 of User P1: PRINT Job, 12 pages, Arrival Time: 3s | Total Processed Pages: 3
Processed page 3 of User P2: SCAN Job, 46 pages, Arrival Time: 1s | Total Processed Pages: 4
Starting User P4: PRINT Job, 2 pages, Arrival Time: 5s
Starting User P3: PRINT Job, 5 pages, Arrival Time: 5s
Processed page 2 of User P1: PRINT Job, 12 pages, Arrival Time: 3s | Total Processed Pages: 5
Processed page 4 of User P2: SCAN Job, 46 pages, Arrival Time: 1s | Total Processed Pages: 6
Starting User P4: PRINT Job, 9 pages, Arrival Time: 6s

```

### With Peterson Synchronization

```
Starting User P2: PRINT Job, 28 pages, Arrival Time: 1s
Starting User P1: SCAN Job, 9 pages, Arrival Time: 1s
Processed page 1 of User P2: PRINT Job, 28 pages, Arrival Time: 1s | Total Processed Pages: 1
Processed page 1 of User P1: SCAN Job, 9 pages, Arrival Time: 1s | Total Processed Pages: 2
Processed page 2 of User P1: SCAN Job, 9 pages, Arrival Time: 1s | Total Processed Pages: 4
Processed page 2 of User P2: PRINT Job, 28 pages, Arrival Time: 1s | Total Processed Pages: 3
Starting User P1: SCAN Job, 9 pages, Arrival Time: 4s
Starting User P3: PRINT Job, 15 pages, Arrival Time: 4s
Starting User P4: PRINT Job, 47 pages, Arrival Time: 4s
Starting User P5: SCAN Job, 15 pages, Arrival Time: 4s
Starting User P2: SCAN Job, 15 pages, Arrival Time: 4s
Processed page 3 of User P2: PRINT Job, 28 pages, Arrival Time: 1s | Total Processed Pages: 5
Processed page 3 of User P1: SCAN Job, 9 pages, Arrival Time: 1s | Total Processed Pages: 6
Starting User P4: SCAN Job, 4 pages, Arrival Time: 5s
Processed page 1 of User P3: PRINT Job, 15 pages, Arrival Time: 4s | Total Processed Pages: 7

```

**There are no race conditions**

There is a difference in speed, for example, semaphores use atomic operations and require context switches between the
kernel and the user, which increases overhead, while Peterson uses flags, Mutex system calls, which gives them an
advantage. And also all the methods switch between jobs a little differently
