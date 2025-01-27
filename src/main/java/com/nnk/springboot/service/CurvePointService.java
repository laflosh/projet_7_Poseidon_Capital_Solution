package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.util.DataValidator;

import jakarta.validation.Valid;

/**
 * This service contain all the methods to execute CRUD operations for the curvepoint domain
 */
@Service
public class CurvePointService {

	private static  final Logger log = LogManager.getLogger(CurvePointService.class);

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Autowired
	private DataValidator dataValidator;

	/**
	 * Fetching all the cruvepoints entity in the database
	 *
	 * @return A list of curvepoints
	 */
	public List<CurvePoint> getAllCurvePoints() {

		log.info("Get all curvepoints in the database.");

		List<CurvePoint> curvePoints = curvePointRepository.findAll();

		return curvePoints;

	}

	/**
	 * Fetching one curvepoint entity depending of the id in the database
	 *
	 * @param id of the curvepoint
	 * @return Curvepoint
	 */
	public CurvePoint getCurvePointById(Integer id) {

		log.info("Get one curvepoint in the database with id : {} .", id);

		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("CurvePoint not found"));

		return curvePoint;

	}

	/**
	 * Add a new curvepoint entity in the database
	 *
	 * @param new curvePoint
	 */
	public void addNewCurvePoint(@Valid CurvePoint curvePoint) {

		if(dataValidator.checkDouble(curvePoint.getTerm().toString()) &&
			dataValidator.checkDouble(curvePoint.getValue().toString()) ) {

			log.info("Add new curvepoint object in database : {} .", curvePoint);

			curvePointRepository.save(curvePoint);

		}

	}

	/**
	 * Update the curvepoint entity existing in the database
	 *
	 * @param update curvePoint
	 */
	public void updateCurvePoint(@Valid CurvePoint curvePoint) {

		if(dataValidator.checkDouble(curvePoint.getTerm().toString()) &&
			dataValidator.checkDouble(curvePoint.getValue().toString())) {

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

	}

	/**
	 * Delete an existing curvepoint entity in the database depending of the id
	 *
	 * @param id of the curvepoint
	 * @return true if delete
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
