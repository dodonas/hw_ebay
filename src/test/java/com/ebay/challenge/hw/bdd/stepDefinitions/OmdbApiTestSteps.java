package com.ebay.challenge.hw.bdd.stepDefinitions;

import com.ebay.challenge.hw.bdd.DTO.error.OmdbErrorResponse;
import com.ebay.challenge.hw.bdd.DTO.response.OmdbResponse;
import com.ebay.challenge.hw.bdd.DTO.search.OmdbSearchResponse;
import com.google.common.net.UrlEscapers;
import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author andrey.dodon
 */


public class OmdbApiTestSteps extends AbstractSteps implements En {

    private ResponseBody omdbResponse;
    private String apiKey;
    private Scenario scenario;

    public OmdbApiTestSteps() {


        Before((Scenario scenario) -> {
            this.scenario = scenario;
        });


        Given("^I set API key to \"([^\"]*)\"$", (String apiKey) -> {
            this.apiKey = apiKey;
        });

        Given("^check \"([^\"]*)\" is accessible$", (String host) -> {
            RestAssured.baseURI = host;
            OmdbErrorResponse ombdErrorResponse = executeGet(Map.ofEntries(getApiKey()), OmdbErrorResponse.class);
            assertThat(ombdErrorResponse.getError()).isEqualTo("Something went wrong.");
        });


        Given("^the user firing the request by year \"([^\"]*)\"$", (String year) -> {
            OmdbErrorResponse ombdErrorResponse = executeGet(Map.ofEntries(
                    getApiKey(),
                    entry("y", UrlEscapers.urlFormParameterEscaper().escape(year))), OmdbErrorResponse.class);
            assertThat(ombdErrorResponse.getError()).isEqualTo("Something went wrong.");
        });


        Given("^the user firing the request by IMDB ID \"([^\"]*)\"$", (String imdbId) -> {
            omdbResponse = executeGet(Map.ofEntries(
                    getApiKey(),
                    entry("i", UrlEscapers.urlFormParameterEscaper().escape(imdbId))));
            assertThat(omdbResponse).isNotNull();
        });


        Given("^the user firing the request by IMDB ID \"([^\"]*)\" and YEAR \"([^\"]*)\"$",
                (String imdbId, String year) -> {
                    omdbResponse = executeGet(Map.ofEntries(
                            getApiKey(),
                            entry("i", UrlEscapers.urlFormParameterEscaper().escape(imdbId)),
                            entry("y", UrlEscapers.urlFormParameterEscaper().escape(year))));
                    assertThat(omdbResponse).isNotNull();
                });

        Given("^the user firing the request by IMDB ID \"([^\"]*)\" YEAR \"([^\"]*)\" and PLOT FULL$",
                (String imdbId, String year) -> {
                    omdbResponse = executeGet(Map.ofEntries(
                            getApiKey(),
                            entry("i", UrlEscapers.urlFormParameterEscaper().escape(imdbId)),
                            entry("plot", "full"),
                            entry("y", UrlEscapers.urlFormParameterEscaper().escape(year))));
                    assertThat(omdbResponse).isNotNull();
                });


        Given("^the user firing the search request by the value \"([^\"]*)\"$", (String searchString) -> {
            omdbResponse = executeGet(Map.ofEntries(
                    getApiKey(),
                    entry("s", UrlEscapers.urlFormParameterEscaper().escape(searchString))));
            assertThat(omdbResponse).isNotNull();
        });


        Then("^the result title contains \"([^\"]*)\"$", (String expectedTitle) -> {
            scenario.write(String.format("Expected title %s, actual title %s", expectedTitle,
                    omdbResponse.as(OmdbResponse.class).getTitle()));
            assertThat(isPosterValid(new URL(omdbResponse.as(OmdbResponse.class).getPoster()))).isTrue();
            scenario.write("Poster is valid");
            assertThat(omdbResponse.as(OmdbResponse.class).getTitle()).isEqualTo(expectedTitle);
        });


        Then("^the result error contains \"([^\"]*)\"$", (String expectedError) -> {
            scenario.write(String.format("Expected error %s, actual error %s", expectedError,
                    omdbResponse.as(OmdbErrorResponse.class).getError()));
            assertThat(omdbResponse.as(OmdbErrorResponse.class).getError()).isEqualTo(expectedError);
        });


        Then("^the total Results field equals \"([^\"]*)\"$", (String totalResults) -> {
            scenario.write(String.format("Expected total results %s, actual total results %s", totalResults,
                    omdbResponse.as(OmdbSearchResponse.class).getTotalResults()));
            assertThat(omdbResponse.as(OmdbSearchResponse.class).getTotalResults()).isEqualTo(totalResults);
        });


        Then("^the plot size is (\\d+)$", (Integer expectedPlotLength) -> {
            scenario.write(String.format("Expected plot length %s, actual plot length (%s) - %s", expectedPlotLength,
                    omdbResponse.as(OmdbResponse.class).getPlot().length(),
                    omdbResponse.as(OmdbResponse.class).getPlot()));
            assertThat(isPosterValid(new URL(omdbResponse.as(OmdbResponse.class).getPoster()))).isTrue();
            scenario.write("Poster is valid");
            assertThat(omdbResponse.as(OmdbResponse.class).getPlot().length()).isEqualTo(expectedPlotLength);
        });


    }

    private static Boolean isPosterValid(URL imgUrl) {
        try {
            BufferedImage image = ImageIO.read(imgUrl);
            if (image != null) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }

    }

    private Map.Entry<String, String> getApiKey() {
        return entry("apikey", apiKey);
    }
}
