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
	RuleNameRepository ruleNameRepository;
	

	public List<RuleName> getAllRuleNames() {
		
		List<RuleName> ruleNames = ruleNameRepository.findAll();
		
		return ruleNames;
	}

	public void addNewRuleName(@Valid RuleName ruleName) {
		
		ruleNameRepository.save(ruleName);
		
	}

	
}
