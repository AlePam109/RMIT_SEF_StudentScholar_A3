// Rohan Chaudhari
package com.studentScholar.persistence;

import com.studentScholar.assessment.Submission;

public class Database {
    private String databaseId;

    public String save(Submission submission) {
        System.out.println("[Database] Saved submission: " + submission.getSubmissionId());
        return submission.getSubmissionId();
    }
}
