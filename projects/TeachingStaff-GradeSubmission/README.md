# Teaching Staff - Grade Submission

Java skeleton implementation of the approved **Grade Submission** sequence diagram.

The project uses an in-memory model so the assessed workflow is visible without a
database, GUI framework, or external similarity-checking provider.

## Import into an IDE

- **Eclipse:** create a Java project from this directory and select `src` as the source folder.
- **IntelliJ IDEA:** open this directory and mark `src` as **Sources Root**.

## Compile and run from a terminal

From this project directory:

```text
javac -d out src/studentscholar/grading/*.java
java -cp out studentscholar.grading.Main
java -ea -cp out studentscholar.grading.GradingWorkflowChecks
```

## Sequence coverage

The demonstration covers access verification, assessment and submission selection,
parallel retrieval of submission content and rubric criteria, optional similarity
checking, standard and custom criterion marks, custom-mark justification, total-mark
calculation, draft persistence, and confirmation.

The `GradeRecord` used by the interaction is initialized in the demonstration fixture
before grading begins. This matches the note attached to `draftGrade:GradeRecord` in
the sequence diagram.

The workflow checks cover successful standard and custom marks, access control,
incomplete grading, pending custom-mark protection, the one-grade-record composition,
and unavailable similarity checking. They use Java assertions and require no external
test library.
