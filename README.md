# Smart Scholarship Tracker

A Java desktop application that manages scholarship applications, checks eligibility
against GPA and income criteria, and notifies stakeholders of status changes.
Built for the CSC61204 Software Construction assignment (SDG 4: Quality Education).

## Features
- Eligibility checking with three rule types (merit, need-based, merit + need)
- Live currency conversion via the Frankfurter exchange-rate API
- Automatic notifications using the Observer pattern
- Swing GUI with three screens (Apply, Result, History)

## Design patterns
- **Strategy** — interchangeable eligibility rules per scholarship type
- **Observer** — automatic status-change notifications

## Technologies
- Java 17, Maven, Swing, JUnit 5, JaCoCo, Gson

## How to run
1. Make sure Java 17 is installed.
2. From the project folder, run: `mvn -B package`
3. Run the program: `java -jar target/smart-scholarship-tracker-1.0-SNAPSHOT.jar`

## How to run the tests
- From the project folder, run: `mvn -B test`
- The coverage report is generated at: `target/site/jacoco/index.html`