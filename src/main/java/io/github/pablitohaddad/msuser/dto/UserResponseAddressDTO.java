package io.github.pablitohaddad.msuser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponseAddressDTO {
    @JsonProperty("cep")
    private String postalCode;

    @JsonProperty("logradouro")
    private String street;

    @JsonProperty("localidade")
    private String city;

    @JsonProperty("uf")
    private String state;
}
