package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {

	private static  final Logger log = LogManager.getLogger(CurvePointService.class);
	
	@Autowired
	CurvePointRepository curvePointRepository;

	public List<CurvePoint> getAllCurvePoints() {
		
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		
		return curvePoints;
		
	}
	
	
	
}
