package com.example.demo.service;

import com.example.demo.entity.Mot;

public interface IService {

	Mot getMot(String mot, String relation);

	Mot GetMotRelation(String mot, String relation);
}
