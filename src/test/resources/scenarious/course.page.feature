Feature:
  Scenario: search and click on course
    Given open browser
    When open course catalog
    When search course
    Then Course title mast be equal to course name

  Scenario: verify Earliest And Latest Courses
    Given open browser
    When open course catalog
    When get Earliest Courses
    When get Latest Courses
    Then verify Earliest And Latest Courses

  Scenario: verify Selected Course
    Given open browser
    When open course catalog
    And hover On Training Field
    Then verify Selected Course

  Scenario: check that cheapest and expensive courses are in page
    Given open browser
    When open course catalog
    And click on Подготовительные курсы
    Then find cheapest and expensive courses
