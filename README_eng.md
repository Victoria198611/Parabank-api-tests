Parabank API Tests

Purpose
A learning and practice project for API testing, built with Java, Maven, RestAssured, and TestNG. Includes Allure reporting and clear documentation for portfolio presentation.

Project Structure
- “base” – configuration of common requests (BaseRequest, BaseTest)
- “endpoints” – classes for each endpoint (TransactionEndpoint, TransactionDetailsEndpoint, AccountTransactionsEndpoint)
- “tests” – test classes organized by scenarios (TransactionTests, TransactionDetailsTests, AccountTransactionsTests)
- “utils” – utilities and configurations (e.g., ConfigManager)

Technologies
- Java 17
- Maven
- RestAssured
- TestNG
- Allure

Test Scenarios
- “TransactionTests” → verifies `/transactions` without ID (missing ID scenario).
- “TransactionDetailsTests” → verifies `/transactions/{id}` with valid, invalid, and non numeric IDs.
- “AccountTransactionsTests” → verifies `/accounts/{id}/transactions`.

Issues Encountered and Solutions
- Cloudflare blocking → Parabank returned HTML instead of XML.  
  Solution: added a valid User Agent (Edge/Chrome) and marked affected tests as “Skipped”.
- “Inherited path params” → RestAssured retained `id=15031` from previous tests.  
  Solution: reset global path params in `BaseRequest.getRequest()`.
- “Allure reporting” → integrated with AllureRestAssured for clear visualization of results.

Environment Limitations
The Parabank demo API is protected by Cloudflare and sometimes responds with HTML or status code 400 to automated requests.  
These are not framework bugs but demo environment limitations.

Affected tests are marked as “Skipped” in the Allure report, with the message:  
"Parabank demo environment blocked request or returned 400."

Allure Categories
For clearer reporting, the framework includes a `categories.json` file in `src/test/resources`.  
This automatically groups results in Allure:
- Environment Blocked → tests marked “Skipped” due to Cloudflare.
- Assertion Errors → tests failed due to validation mismatches.
- Timeouts → tests failed due to response delays.
- Connection Issues → network or connectivity problems.
- Other Errors → any unclassified errors.

GitHub Actions Integration
Workflow 1: API Tests CI
- Runs tests on every push or pull request.
- Caches Maven and Allure history.
- Saves Allure results as artifacts, downloadable from GitHub Actions.

Workflow 2: API Tests and Deploy Allure Report
- Runs tests and generates the Allure report.
- Automatically publishes the report to GitHub Pages.
- Publicly available report: [Allure Report](https://victoria198611.github.io/ParabankAPITests)

The `executor.json` file is configured to display in the Allure Executors tab:
- direct link to workflow
- link to the current build
- link to the published report

Run Instructions
Bash:
mvn clean test
mvn allure:serve

Conclusion
The framework is stable, tests are isolated, and documentation reflects both technical aspects and resolved challenges. Integration with GitHub Actions at two levels (CI + Deploy) and publishing Allure reports on GitHub Pages demonstrate practical experience in QA Automation and CI/CD.

Author
Victoria — QA Automation Tester Focus: Learning and practicing API testing, with emphasis on clear reporting and well structured documentation.
