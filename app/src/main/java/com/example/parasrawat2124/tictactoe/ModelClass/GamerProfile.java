package com.example.parasrawat2124.tictactoe.ModelClass;

public class GamerProfile {

    String name;
    String email;
    String uri;

    public GamerProfile() {

    }

    public GamerProfile(String name, String email, String uri) {
        this.name = name;
        this.email = email;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUri() {
        return uri;
    }
}
