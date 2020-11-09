package com.findriver.docubaseapp;

class AffichageListe {
    private String titre, nom, description;

    public AffichageListe(String titre, String nom, String description) {
        this.titre = titre;
        this.nom = nom;
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }
}
