package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import jakarta.validation.Valid;

/**
 * 
 */
@Service
public class CurvePointService {

	private static  final Logger log = LogManager.getLogger(CurvePointService.class);
	
	@Autowired
	CurvePointRepository curvePointRepository;

	public List<CurvePoint> getAllCurvePoints() {
		
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		
		return curvePoints;
		
	}
	
	public CurvePoint getCurvePointById(Integer id) {
		
		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("CurvePoint not found"));
		
		return curvePoint;
		
	}

	public void addNewCurvePoint(@Valid CurvePoint curvePoint) {
		
		curvePointRepository.save(curvePoint);
		
	}

	public void updateCurvePoint(@Valid CurvePoint curvePoint) {
		
		CurvePoint existingCurvePoint = getCurvePointById(curvePoint.getId());
		
		existingCurvePoint.setTerm(curvePoint.getTerm());
		existingCurvePoint.setValue(curvePoint.getValue());
		
		curvePointRepository.save(existingCurvePoint);
		
	}
	
}
