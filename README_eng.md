Parabank API Tests

Purpose
A learning and practice project for API testing, built with Java, Maven, RestAssured, and TestNG. Includes Allure reporting and clear documentation for portfolio presentation.

Project Structure
•	base – common request configuration (BaseRequest, BaseTest)
•	endpoints – classes for each endpoint (TransactionEndpoint, TransactionDetailsEndpoint, AccountTransactionsEndpoint)
•	tests – test classes organized by scenarios (TransactionTests, TransactionDetailsTests, AccountTransactionsTests)
•	utils – utilities and configuration (e.g., ConfigManager)

Technologies
•	Java 17
•	Maven
•	RestAssured
•	TestNG
•	Allure

Test Scenarios
•	TransactionTests → verifies /transactions without ID (missing ID scenario).
•	TransactionDetailsTests → verifies /transactions/{id} with valid, invalid, and non numeric IDs.
•	AccountTransactionsTests → verifies /accounts/{id}/transactions.

Issues Encountered and Solutions
•	Cloudflare blocking → Parabank returned HTML instead of XML.
•	Solution: adding a valid User-Agent (Edge/Chrome).
•	Inherited path params → RestAssured retained id=15031 from previous tests.
•	 Solution: global reset of path params in BaseRequest.getRequest().
•	Allure reporting → integrated with AllureRestAssured for clear visualization of results.

GitHub Actions Integration
Workflow 1: API Tests CI
•	Runs tests on every push or pull request.
•	Caches Maven dependencies and Allure history.
•	Saves Allure results as artifacts, downloadable from GitHub Actions.
Workflow 2: API Tests and Deploy Allure Report
•	Runs tests and generates the Allure report.
•	Publishes the report automatically on GitHub Pages.
•	Public report available here:  Allure Report (victoria198611.github.io in Bing)
The executor.json file is configured to display in Allure’s Executors tab:
•	direct link to the workflow
•	link to the current build
•	link to the published report

Run Instructions
bash
mvn clean test
mvn allure:serve

Conclusion
The framework is stable, tests are isolated, and documentation reflects both technical aspects and resolved challenges. Integration with GitHub Actions at two levels (CI + Deploy) and publishing Allure reports on GitHub Pages demonstrate practical experience in QA Automation and CI/CD.
