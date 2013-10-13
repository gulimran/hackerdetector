Feature: hacker detection
  As a system security administrator
  I want to see the IP address of the hacking source
  So that I can identify suspicious activity

  Scenario: identify a single IP address that has attempted a failed login 5 or more times within a 5 minute period
    Given less than 5 failed login attempts are allowed within 5 minutes
    When 6 failed activity logs are recorded for the same IP address
    Then HackerDetector returns that suspicious IP address

  Scenario: do not identify an IP address that has attempted a failed login less than 5 times within a 5 minute period
    Given less than 5 failed login attempts are allowed within 5 minutes
    When 3 failed activity logs are recorded for the same IP address
    Then HackerDetector returns no IP address