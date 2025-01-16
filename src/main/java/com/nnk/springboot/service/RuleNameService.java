package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import jakarta.validation.Valid;

/**
 *
 */
@Service
public class RuleNameService {

	private static  final Logger log = LogManager.getLogger(RuleNameService.class);

	@Autowired
	private RuleNameRepository ruleNameRepository;


	/**
	 * @return
	 */
	public List<RuleName> getAllRuleNames() {

		List<RuleName> ruleNames = ruleNameRepository.findAll();

		return ruleNames;

	}

	/**
	 * @param id
	 * @return
	 */
	public RuleName getRuleNameById(Integer id) {

		RuleName ruleName = ruleNameRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("RuleName not found."));

		return ruleName;

	}

	/**
	 * @param ruleName
	 */
	public void addNewRuleName(@Valid RuleName ruleName) {

		ruleNameRepository.save(ruleName);

	}

	/**
	 * @param ruleName
	 */
	public void updateRuleName(@Valid RuleName ruleName) {

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

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteRuleName(Integer id) {

		if(ruleNameRepository.existsById(id)) {

			ruleNameRepository.deleteById(id);

			return true;

		}

		return false;
	}

}
