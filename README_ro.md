Parabank API Tests

Scop
Proiect de învățare și practică pentru testarea API urilor, construit cu Java, Maven, RestAssured și TestNG. Include raportare Allure și documentație clară pentru portofoliu.

Structura proiectului
- base – configurarea requesturilor comune (BaseRequest, BaseTest)
- endpoints** – clase pentru fiecare endpoint (TransactionEndpoint, TransactionDetailsEndpoint, AccountTransactionsEndpoint)
- "tests" – clase de test organizate pe scenarii (TransactionTests, TransactionDetailsTests, AccountTransactionsTests)
- "utils" – utilitare și configurări (ex. ConfigManager)

Tehnologii
- Java 17
- Maven
- RestAssured
- TestNG
- Allure

Scenarii de test
- "TransactionTests" → verifică `/transactions` fără ID (scenariu missing ID).
- "TransactionDetailsTests" → verifică `/transactions/{id}` cu ID valid, invalid și non numeric.
- "AccountTransactionsTests" → verifică `/accounts/{id}/transactions`.

Probleme întâlnite și soluții
- "Cloudflare blocking" → Parabank returna HTML în loc de XML.  
  Soluție: adăugarea unui User Agent valid (Edge/Chrome) și marcarea testelor ca *Skipped*.
- "Path params moștenite" → RestAssured păstra `id=15031` din testele anterioare.  
  Soluție: resetarea globală a path params în `BaseRequest.getRequest()`.
- "Raportare Allure" → integrată cu AllureRestAssured pentru vizualizare clară a rezultatelor.

Environment Limitations
Parabank demo API este protejat de Cloudflare și uneori răspunde cu HTML sau cod 400 la cererile automate.  
Acestea nu sunt bug uri ale framework ului, ci limitări ale mediului demo.

Testele afectate sunt marcate ca "Skipped" în raportul Allure, cu mesajul:  
"Parabank demo environment blocked request or returned 400."

Allure Categories
Pentru o raportare mai clară, framework-ul include fișierul `categories.json` în `src/test/resources`.  
Acesta grupează automat rezultatele în Allure:
- Environment Blocked → teste marcate “Skipped” din cauza Cloudflare.
- Assertion Errors → teste eșuate din cauza validărilor.
- Timeouts → teste eșuate din cauza timpului de răspuns.
- Connection Issues → probleme de rețea sau conexiune.
- Other Errors → orice alte erori neclasificate.

Integrare GitHub Actions
Workflow 1: API Tests CI
- Rulează testele pe fiecare push sau pull request.
- Cache pentru Maven și Allure history.
- Rezultatele Allure sunt salvate ca artifact și pot fi descărcate din GitHub Actions.

Workflow 2: API Tests and Deploy Allure Report
- Rulează testele și generează raportul Allure.
- Publică raportul automat pe GitHub Pages.
- Raportul este disponibil public:
  [Allure Report] (https://victoria198611.github.io/ParabankAPITests)

Fișierul `executor.json` este configurat pentru a afișa în Allure tab-ul Executors:
- link direct către workflow
- link către build ul curent
- link către raportul publicat

Instrucțiuni de rulare
Bash:
mvn clean test
mvn allure:serve

Concluzie:
Framework-ul este stabil, testele sunt izolate și documentația reflectă atât partea tehnică, cât și provocările rezolvate. Integrarea cu GitHub Actions pe două niveluri (CI + Deploy) și publicarea rapoartelor Allure pe GitHub Pages demonstrează experiență practică în QA Automation și CI/CD.

Autor:
Victoria — QA Automation Tester
Focus: Învățare și practică în testarea API, cu accent pe raportare clară și documentație bine structurată.