package com.example.interviewMe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "utente")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Column(nullable = false)
    private String cognome;



    //@JsonManagedReference
    //    @OneToMany(mappedBy = "utente",cascade = CascadeType.ALL,orphanRemoval = true)

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Colloquio> colloqui = new ArrayList<>();



    //@JsonProperty("colloqui")
    public List<Colloquio> getColloqui() {
        return this.colloqui;
    }


    public void setColloqui(List<Colloquio> colloqui) {
        this.colloqui = colloqui;
    }

    public Utente(String username, String password, String nome, String cognome) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Utente(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

}
