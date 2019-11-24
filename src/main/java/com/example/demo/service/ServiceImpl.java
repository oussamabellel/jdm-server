package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Mot;
import com.example.demo.metier.Methods;

@Service
public class ServiceImpl implements IService {

	public ServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Mot getMot(String mot, String relation) {
		// TODO Auto-generated method stub
		Methods methods = new Methods();
		return methods.Parser(mot, relation);
	}

	@Override
	public Mot GetMotRelation(String mot, String relation) {
		// TODO Auto-generated method stub
		return null;
	}

}
