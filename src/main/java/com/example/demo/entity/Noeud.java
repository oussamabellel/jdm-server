package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Noeud {

	public int id;
	public String nom;
	public NoeudType type;
	public int poids;
	public String motFormate;

	public Noeud(int id, String nom, NoeudType type, int poids, String motFormate) {
		super();
		this.id = id;
		this.nom = nom;
		this.type = type;
		this.poids = poids;
		this.motFormate = motFormate;

	}

	public Noeud(int id, String nom, int poids) {
		super();
		this.id = id;
		this.nom = nom;
		this.poids = poids;

	}

	@JsonIgnore
	public NoeudType getType() {
		return type;
	}

	public void setType(NoeudType type) {
		this.type = type;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

	@JsonIgnore
	public String getMotFormate() {
		return motFormate;
	}

	public void setMotFormate(String motFormate) {
		this.motFormate = motFormate;
	}

	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		String res = String.valueOf(id) + ";";
		res += "'" + nom + "';";
		res += String.valueOf(type) + ";";
		res += String.valueOf(poids);
		if (!nom.equals(motFormate)) {
			res += ";" + motFormate;
		}
		return res;
	}
}
