package com.blah.userservice.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String firstname;

    private String lastname;

    User() {
    }

    @JsonCreator
    public User(@JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
