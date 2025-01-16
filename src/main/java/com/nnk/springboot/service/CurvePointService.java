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
	private CurvePointRepository curvePointRepository;

	/**
	 * @return
	 */
	public List<CurvePoint> getAllCurvePoints() {

		log.info("Get all curvepoints in the database.");
		
		List<CurvePoint> curvePoints = curvePointRepository.findAll();

		return curvePoints;

	}

	/**
	 * @param id
	 * @return
	 */
	public CurvePoint getCurvePointById(Integer id) {

		log.info("Get one curvepoint in the database with id : {} .", id);
		
		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("CurvePoint not found"));

		return curvePoint;

	}

	/**
	 * @param curvePoint
	 */
	public void addNewCurvePoint(@Valid CurvePoint curvePoint) {

		log.info("Add new curvepoint object in database : {} .", curvePoint);
		
		curvePointRepository.save(curvePoint);

	}

	/**
	 * @param curvePoint
	 */
	public void updateCurvePoint(@Valid CurvePoint curvePoint) {

		log.info("Update the curvepoint object existing in the database with id : {} .", curvePoint.getId());
		
		CurvePoint existingCurvePoint = getCurvePointById(curvePoint.getId());

		if(curvePoint.getTerm() != existingCurvePoint.getTerm()) {
			existingCurvePoint.setTerm(curvePoint.getTerm());
		}
		
		if(curvePoint.getValue() != existingCurvePoint.getValue()) {
			existingCurvePoint.setValue(curvePoint.getValue());
		}

		curvePointRepository.save(existingCurvePoint);

	}

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteCurvePoint(Integer id) {

		log.info("Delete the curvepoint object in the database with id : {} .", id);
		
		if(curvePointRepository.existsById(id)) {

			curvePointRepository.deleteById(id);

			return true;

		}

		return false;
	}

}
