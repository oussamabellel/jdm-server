package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Mot;
import com.example.demo.service.IService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServiceController {

	@Autowired
	private IService iService;

	@RequestMapping("api/word")
	public Mot getMot(@RequestParam(value = "mot") String mot,
			@RequestParam(required = false, value = "relation") String relation) {

		return iService.getMot(mot, relation);
	}

	@RequestMapping(value = "/api/relation")
	public Mot GetMotRelation(@RequestParam(value = "mot") String mot,
			@RequestParam(value = "relation") String relation) throws Exception {

		return iService.GetMotRelation(mot, relation);
	}

}
