// Alec Pham
package com.studentScholar.grading;

/**
 * Small deterministic substitute for the external similarity provider.
 * A real implementation would delegate through the adapter defined by the team model.
 */
public final class SimilarityCheckService {
    private final boolean available;

    public SimilarityCheckService(boolean available) {
        this.available = available;
    }

    public SimilarityReport analyse(String content) throws SimilarityServiceException {
        if (!available) {
            throw new SimilarityServiceException("Similarity service is unavailable");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new SimilarityServiceException("Submission content is unsupported");
        }

        // Deterministic sample result: this is skeleton behaviour, not real analysis.
        return new SimilarityReport(
                "SIM-001",
                12.5,
                "Similarity analysis completed by the stub service");
    }
}
