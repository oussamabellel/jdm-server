package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Relation {

	public int idRelation;
	public Noeud noeud;
	public RelationType type;
	public int poids;

	public Relation(int idRelation, Noeud noeud, RelationType type, int poids) {
		super();
		this.idRelation = idRelation;
		this.noeud = noeud;
		this.type = type;
		this.poids = poids;
	}

	public int getIdRelation() {
		return idRelation;
	}

	public void setIdRelation(int idRelation) {
		this.idRelation = idRelation;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	@JsonIgnore
	public RelationType getType() {
		return type;
	}

	public void setType(RelationType type) {
		this.type = type;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

}
