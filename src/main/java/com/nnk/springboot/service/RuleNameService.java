package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.util.DataValidator;

import jakarta.validation.Valid;

/**
 * This service contain all the methods to execute CRUD operations for the rulename domain
 */
@Service
public class RuleNameService {

	private static  final Logger log = LogManager.getLogger(RuleNameService.class);

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	private DataValidator dataValidator;


	/**
	 * Fetching all the rulenames entity in the database
	 *
	 * @return A list of rulenames
	 */
	public List<RuleName> getAllRuleNames() {

		log.info("Get all rulenames in the database.");

		List<RuleName> ruleNames = ruleNameRepository.findAll();

		return ruleNames;

	}

	/**
	 * Fetching one rulename entity depending of the id in the database
	 *
	 * @param id of the rulename
	 * @return rulename
	 */
	public RuleName getRuleNameById(Integer id) {

		log.info("Get one rulename by id : {} .", id);

		RuleName ruleName = ruleNameRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("RuleName not found."));

		return ruleName;

	}

	/**
	 * Add new valid rulename entity in the database
	 *
	 * @param new ruleName
	 */
	public void addNewRuleName(@Valid RuleName ruleName) {

		if(dataValidator.checkString(ruleName.getName()) &&
			dataValidator.checkString(ruleName.getDescription()) &&
			dataValidator.checkString(ruleName.getJson()) &&
			dataValidator.checkString(ruleName.getTemplate()) &&
			dataValidator.checkString(ruleName.getSqlPart()) &&
			dataValidator.checkString(ruleName.getSqlStr())) {

			log.info("Add new rulename object in the database : {} .", ruleName);

			ruleNameRepository.save(ruleName);

		}

	}

	/**
	 * Update a valid existing rulename entity in the database
	 *
	 * @param update ruleName
	 */
	public void updateRuleName(@Valid RuleName ruleName) {

		if(dataValidator.checkString(ruleName.getName()) &&
			dataValidator.checkString(ruleName.getDescription()) &&
			dataValidator.checkString(ruleName.getJson()) &&
			dataValidator.checkString(ruleName.getTemplate()) &&
			dataValidator.checkString(ruleName.getSqlPart()) &&
			dataValidator.checkString(ruleName.getSqlStr())) {

			log.info("Update the rulename object existing in the database with id : {} .", ruleName.getId());

			RuleName existingRuleName = getRuleNameById(ruleName.getId());

			if(ruleName.getName() != existingRuleName.getName()) {
				existingRuleName.setName(ruleName.getName());
			}

			if(ruleName.getDescription() != existingRuleName.getDescription()) {
				existingRuleName.setDescription(ruleName.getDescription());
			}

			if(ruleName.getJson() != existingRuleName.getJson()) {
				existingRuleName.setJson(ruleName.getJson());
			}

			if(ruleName.getTemplate() != existingRuleName.getTemplate()) {
				existingRuleName.setTemplate(ruleName.getTemplate());
			}

			if(ruleName.getSqlStr() != existingRuleName.getSqlStr()) {
				existingRuleName.setSqlStr(ruleName.getSqlStr());
			}

			if(ruleName.getSqlPart() != existingRuleName.getSqlPart()) {
				existingRuleName.setSqlPart(ruleName.getSqlPart());
			}

			ruleNameRepository.save(existingRuleName);

		}

	}

	/**
	 * Delete an existing rulename entity in the database depending of the id
	 *
	 * @param id of the rulename
	 * @return true if delete
	 */
	public boolean deleteRuleName(Integer id) {

		log.info("Delete the rulename object in the database with the id : {} .", id);

		if(ruleNameRepository.existsById(id)) {

			ruleNameRepository.deleteById(id);

			return true;

		}

		return false;
	}

}
