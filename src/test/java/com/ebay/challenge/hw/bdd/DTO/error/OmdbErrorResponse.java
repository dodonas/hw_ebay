package com.ebay.challenge.hw.bdd.DTO.error;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andrey.dodon
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Response",
        "Error"
})
public class OmdbErrorResponse {

    @JsonProperty("Response")
    private String response;
    @JsonProperty("Error")
    private String error;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("Response")
    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty("Error")
    public String getError() {
        return error;
    }

    @JsonProperty("Error")
    public void setError(String error) {
        this.error = error;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("response", response).append("error", error).append("additionalProperties", additionalProperties).toString();
    }

}
