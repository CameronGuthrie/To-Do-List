package com.qa.tdl.controller;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.tdl.dto.ItemDto;
import com.qa.tdl.persistance.domain.Item;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Sql(scripts = { "classpath:TDL-schema.sql","classpath:TDL-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ItemControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private ObjectMapper jsonifier;
	
	@Autowired
	private ModelMapper mapper;
	
	private ItemDto mapToDTO(Item item) {
		return this.mapper.map(item, ItemDto.class);
	}
	
	private final String URI = "/item";
	
	// items from data.sql
	private final Item dataItem1 = new Item(1L, "Foo", false);
	private final Item dataItem2 = new Item(2L, "Bar", false);
	private final Item dataItem3 = new Item(3L, "Lorem", false);
	private final Item dataItem4 = new Item(4L, "Ipsum", false);
	
	@Test
	void createTest() throws Exception {
		
		Long toDoListId = 1L;
		Item testItem = new Item("Foo", false);
		ItemDto testDto = this.mapToDTO(testItem);
		testDto.setId(5L);
		
		
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders
				.request(HttpMethod.POST, URI + "/create" + "/" + toDoListId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(testItem))
				.accept(MediaType.APPLICATION_JSON);
		
		
		ResultMatcher status = 
				MockMvcResultMatchers
				.status()
				.isCreated();
		
		ResultMatcher contents = 
				MockMvcResultMatchers
				.content()
				.json(this.jsonifier.writeValueAsString(testDto));
		
		mock.perform(mockRequest)
		.andExpect(status)
		.andExpect(contents);
	}
}
