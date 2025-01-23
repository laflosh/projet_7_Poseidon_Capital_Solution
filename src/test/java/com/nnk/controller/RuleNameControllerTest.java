package com.nnk.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nnk.springboot.Application;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class RuleNameControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	RuleNameRepository ruleNameRepository;

	@Test
	@WithMockUser
	public void getAllRuleNamesInDatabaseAndReturnListPageWithData() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("ruleNames"))
			.andExpect(MockMvcResultMatchers.view().name("ruleName/list"));

	}

	@Test
	@WithMockUser
	public void getAccessToTheAddingPageAndCountainEmptyEntity() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
			.andExpect(MockMvcResultMatchers.view().name("ruleName/add"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void postNewValidRuleNameAndRedirectToThePageList() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("name", "NAME")
				.param("description", "DESCRIPTION")
				.param("json", "JSON")
				.param("template", "TEMPLATE")
				.param("sqlStr", "SQLSTR")
				.param("sqlPart", "SQLPART"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/ruleName/list"));

		//Get the last rulename save in database
		List<RuleName> ruleNames = ruleNameRepository.findAll();
		RuleName ruleName = ruleNames.get(ruleNames.size() - 1);

		//Delete the object who was created
		ruleNameRepository.delete(ruleName);

	}

	@Test
	@WithMockUser
	public void postNoneValidateRuleNamedAndShowFieldsError() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("ruleName","name", "description", "json", "template", "sqlStr", "sqlPart"))
			.andExpect(MockMvcResultMatchers.view().name("ruleName/add"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void getTheUpdatePageAndCountainTheObject() throws Exception {

		//Save a new rating entity
		RuleName newRuleName = new RuleName("NAME", "DESCRIPTION", "JSON", "TEMPLATE", "SQLSTR", "SQLPART");
		ruleNameRepository.save(newRuleName);

		//Get the last rulename save in database
		List<RuleName> ruleNames = ruleNameRepository.findAll();
		RuleName ruleName = ruleNames.get(ruleNames.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/" + ruleName.getId()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
			.andExpect(MockMvcResultMatchers.view().name("ruleName/update"));

		//Delete the object who was created
		ruleNameRepository.delete(ruleName);

	}

	@Test
	@WithMockUser
	@Transactional
	public void postValidateUpdateRuleNameAndRedirectToThePageList() throws Exception {

		//Save a new rating entity
		RuleName newRuleName = new RuleName("NAME", "DESCRIPTION", "JSON", "TEMPLATE", "SQLSTR", "SQLPART");
		ruleNameRepository.save(newRuleName);

		//Get the last rulename save in database
		List<RuleName> ruleNames = ruleNameRepository.findAll();
		RuleName ruleName = ruleNames.get(ruleNames.size() - 1);

		//Testing request

		mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/" + ruleName.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("name", "NAME")
				.param("description", "description")
				.param("json", "JSON")
				.param("template", "template")
				.param("sqlStr", "SQLSTR")
				.param("sqlPart", "SQLPART"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/ruleName/list"));

		//Delete the object who was created
		ruleNameRepository.delete(ruleName);

	}

	@Test
	@WithMockUser
	@Transactional
	public void postNoneValidateUpdateRuleNameAndShowFieldsError() throws Exception {

		//Save a new rating entity
		RuleName newRuleName = new RuleName("NAME", "DESCRIPTION", "JSON", "TEMPLATE", "SQLSTR", "SQLPART");
		ruleNameRepository.save(newRuleName);

		//Get the last rulename save in database
		List<RuleName> ruleNames = ruleNameRepository.findAll();
		RuleName ruleName = ruleNames.get(ruleNames.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/" + ruleName.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("ruleName","name", "description", "json", "template", "sqlStr", "sqlPart"))
			.andExpect(MockMvcResultMatchers.view().name("ruleName/update"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void deleteAnExistingRuleNameObjectInTheDatabase() throws Exception {

		//Save a new rating entity
		RuleName newRuleName = new RuleName("NAME", "DESCRIPTION", "JSON", "TEMPLATE", "SQLSTR", "SQLPART");
		ruleNameRepository.save(newRuleName);

		//Get the last rulename save in database
		List<RuleName> ruleNames = ruleNameRepository.findAll();
		RuleName ruleName = ruleNames.get(ruleNames.size() - 1);

		//Testing Request
		mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/" + ruleName.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/ruleName/list"));

	}
}
