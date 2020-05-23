Feature: OMDB API testing feature

  Background:
    Given I set API key to "772f549d"
    And check "http://www.omdbapi.com/" is accessible

  @Positive
  Scenario: Verify OMDB API works properly when queering by IMDB ID

    Given the user firing the request by IMDB ID "tt0083658"
    Then the result title contains "Blade Runner"

  @Positive
  Scenario: Verify OMDB API works properly when queering by IMDB ID and YEAR

    Given the user firing the request by IMDB ID "tt0083658" and YEAR "2020"
    Then the result title contains "Blade Runner"

  @Positive
  Scenario: Verify OMDB API works properly when search by some string

    Given the user firing the search request by the value "termina"
    Then the total Results field equals "7"

  @Positive
  Scenario: Verify OMDB API works properly when search by IMDB ID, YEAR and PLOT FULL

    Given the user firing the request by IMDB ID "tt0083658" YEAR "2020" and PLOT FULL
    Then the plot size is 807

  @Negative
  Scenario: Verify OMDB API works properly when queering by IMDB ID and YEAR with wrong API key

    Given I set API key to "772f_549d"
    Given the user firing the request by IMDB ID "tt0083658" and YEAR "2020"
    Then the result error contains "Invalid API key!"


