// Prabhuta
package com.studentScholar.enrolment;

import java.util.ArrayList;
import java.util.List;

/** Entity holding the uploaded enrolment CSV and the student emails read from it. */
public class EnrolmentCSV {
    // Prabhuta
    public String fileName;
    public int studentCount;

    // Prabhuta
    public boolean uploadCSV(String file) {
        this.fileName = file;
        return file != null && !file.trim().isEmpty();
    }

    // Prabhuta
    // Skeleton: a missing or non-.csv file gives an empty list (invalid enrolment data).
    public List<String> readEmails() {
        List<String> emails = new ArrayList<>();
        if (fileName == null || !fileName.trim().toLowerCase().endsWith(".csv")) {
            studentCount = 0;
            return emails;
        }
        emails.add("student1@example.com");
        emails.add("student2@example.com");
        studentCount = emails.size();
        return emails;
    }
}
