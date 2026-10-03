package studentscholar.grading;

/** Signals the unavailable/unsupported branch in the similarity-checking fragment. */
public final class SimilarityServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public SimilarityServiceException(String message) {
        super(message);
    }
}
