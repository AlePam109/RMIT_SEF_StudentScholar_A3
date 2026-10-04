// Rohan Chaudhari
package com.studentScholar.persistence;

import java.io.File;

public class FileStorage {
    private String storageId;

    public void save(File file) {
        System.out.println("[FileStorage] Saved file: " + file.getName());
    }
}
