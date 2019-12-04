package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mot extends Noeud {

	ArrayList<String> definition;
	Map<String, List<Relation>> mapEntrantes;
	Map<String, List<Relation>> mapSortantes;
	ArrayList<String> rafDefinitions;

	public Mot(int id, String nom, NoeudType type, int poids, String motFormate, ArrayList<String> definition,
			Map<String, List<Relation>> mapEntrantes, Map<String, List<Relation>> mapSortantes,ArrayList<String> rafDefinitions) {
		super(id, nom, type, poids, motFormate);
		this.definition = definition;
		this.mapEntrantes = mapEntrantes;
		this.mapSortantes = mapSortantes;
		this.rafDefinitions=rafDefinitions;
	}
	
	

	public ArrayList<String> getRafDefinitions() {
		return rafDefinitions;
	}



	public void setRafDefinitions(ArrayList<String> rafDefinitions) {
		this.rafDefinitions = rafDefinitions;
	}



	public Map<String, List<Relation>> getMapEntrantes() {
		return mapEntrantes;
	}

	public void setMapEntrantes(Map<String, List<Relation>> mapEntrantes) {
		this.mapEntrantes = mapEntrantes;
	}

	public Map<String, List<Relation>> getMapSortantes() {
		return mapSortantes;
	}

	public void setMapSortantes(Map<String, List<Relation>> mapSortantes) {
		this.mapSortantes = mapSortantes;
	}

	public ArrayList<String> getDefinition() {
		return definition;
	}

	public void setDefinition(ArrayList<String> definition) {
		this.definition = definition;
	}

}