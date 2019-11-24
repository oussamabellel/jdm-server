package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RelationType {

	public int id;
	public String name, trgpname, rthelp;

	public RelationType(int id, String name, String trgpname, String rthelp) {
		super();
		this.id = id;
		this.name = name;
		this.trgpname = trgpname;
		this.rthelp = rthelp;
	}

	public RelationType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getTrgpname() {
		return trgpname;
	}

	public void setTrgpname(String trgpname) {
		this.trgpname = trgpname;
	}

	@JsonIgnore
	public String getRthelp() {
		return rthelp;
	}

	public void setRthelp(String rthelp) {
		this.rthelp = rthelp;
	}

}
