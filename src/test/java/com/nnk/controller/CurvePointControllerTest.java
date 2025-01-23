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
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class CurvePointControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	CurvePointRepository curvePointRepository;

	@Test
	@WithMockUser
	public void getAllCurvePointsInDatabaseAndReturnListPageWithData() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("curvePoints"))
			.andExpect(MockMvcResultMatchers.view().name("curvePoint/list"));

	}

	@Test
	@WithMockUser
	public void getAccessToTheAddingPageAndCountainEmptyEntity() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
			.andExpect(MockMvcResultMatchers.view().name("curvePoint/add"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void postNewValidCurvePointAndRedirectToThePageList() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("term", "1")
				.param("value", "1"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"));

		//Get the last curvepoint save in database
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		CurvePoint curvePoint = curvePoints.get(curvePoints.size() - 1);

		//Delete the object who was created
		curvePointRepository.delete(curvePoint);

	}

	@Test
	@WithMockUser
	public void postNoneValidateCurvePointdAndShowFieldsError() throws Exception {

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("curvePoint","term", "value"))
			.andExpect(MockMvcResultMatchers.view().name("curvePoint/add"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void getTheUpdatePageAndCountainTheObject() throws Exception {

		//Save a new rating entity
		CurvePoint newCurvePoint = new CurvePoint(1, 1.0 , 1.0);
		curvePointRepository.save(newCurvePoint);

		//Get the last curvepoint save in database
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		CurvePoint curvePoint = curvePoints.get(curvePoints.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/" + curvePoint.getId()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"))
			.andExpect(MockMvcResultMatchers.view().name("curvePoint/update"));

		//Delete the object who was created
		curvePointRepository.delete(curvePoint);

	}

	@Test
	@WithMockUser
	@Transactional
	public void postValidateUpdateCurvePointAndRedirectToThePageList() throws Exception {

		//Save a new rating entity
		CurvePoint newCurvePoint = new CurvePoint(1, 1.0 , 1.0);
		curvePointRepository.save(newCurvePoint);

		//Get the last curvepoint save in database
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		CurvePoint curvePoint = curvePoints.get(curvePoints.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/" + curvePoint.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("term", "2.0")
				.param("value", "2.0"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"));

		//Delete the object who was created
		curvePointRepository.delete(curvePoint);

	}

	@Test
	@WithMockUser
	@Transactional
	public void postNoneValidateUpdateCurvePointAndShowFieldsError() throws Exception {

		//Save a new rating entity
		CurvePoint newCurvePoint = new CurvePoint(1, 1.0 , 1.0);
		curvePointRepository.save(newCurvePoint);

		//Get the last curvepoint save in database
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		CurvePoint curvePoint = curvePoints.get(curvePoints.size() - 1);

		//Testing request
		mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/" + curvePoint.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model()
					.attributeHasFieldErrors("curvePoint","term", "value"))
			.andExpect(MockMvcResultMatchers.view().name("curvePoint/update"));

	}

	@Test
	@WithMockUser
	@Transactional
	public void deleteAnExistingCurvePointObjectInTheDatabase() throws Exception {

		//Save a new rating entity
		CurvePoint newCurvePoint = new CurvePoint(1, 1.0 , 1.0);
		curvePointRepository.save(newCurvePoint);

		//Get the last curvepoint save in database
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		CurvePoint curvePoint = curvePoints.get(curvePoints.size() - 1);

		//Testing Request
		mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/" + curvePoint.getId())
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"));

	}

}
