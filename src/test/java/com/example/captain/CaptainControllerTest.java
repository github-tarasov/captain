package com.example.captain;

import com.example.captain.controller.CaptainController;
import com.example.captain.domain.Captain;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CaptainControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void crudTest() throws Exception {
		// set captain
		Captain create = new Captain(null, 1L, "Иванов Иван");
		MockHttpServletRequestBuilder createBuilder =
			MockMvcRequestBuilders.post("/v1/setCaptain/5")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(create));
		mvc.perform(createBuilder)
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"success\":true")));

		// check captain
		MockHttpServletRequestBuilder readBuilder =
			MockMvcRequestBuilders.get("/v1/captain/5")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(create));
		mvc.perform(readBuilder)
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"participantId\":1")))
			.andExpect(content().string(containsString("\"participantIdentifier\":\"Иванов Иван\"")));

		// remove captain
		MockHttpServletRequestBuilder removeBuilder =
			MockMvcRequestBuilders.post("/v1/removeCaptain/5")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(create));
		mvc.perform(removeBuilder)
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"success\":true")));

		// check captain removed
		MockHttpServletRequestBuilder readBuilder2 =
			MockMvcRequestBuilders.get("/v1/captain/5")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(create));
		mvc.perform(readBuilder2)
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"exists\":false")));
	}

	public void oneCaptainTest() throws Exception {
		// set captain 1
		Captain create = new Captain(null, 1L, "Иванов Иван");
		MockHttpServletRequestBuilder createBuilder =
				MockMvcRequestBuilders.post("/v1/setCaptain/5")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(create));
		mvc.perform(createBuilder)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"success\":true")));

		// set captain 2 for same team
		Captain create2 = new Captain(null, 2L, "Петров Петр");
		MockHttpServletRequestBuilder createBuilder2 =
				MockMvcRequestBuilders.post("/v1/setCaptain/5")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(create2));
		mvc.perform(createBuilder2)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"success\":false")));

		// check captain
		MockHttpServletRequestBuilder readBuilder =
				MockMvcRequestBuilders.get("/v1/captain/5")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(create));
		mvc.perform(readBuilder)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"participantId\":1")))
				.andExpect(content().string(containsString("\"participantIdentifier\":\"Иванов Иван\"")));
	}

	public void otherTeamCaptainTest() throws Exception {
		// set captain 1
		Captain create = new Captain(null, 1L, "Иванов Иван");
		MockHttpServletRequestBuilder createBuilder =
				MockMvcRequestBuilders.post("/v1/setCaptain/5")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(create));
		mvc.perform(createBuilder)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"success\":true")));

		// set captain 1 for other team
		Captain create2 = new Captain(null, 1L, "Иванов Иван");
		MockHttpServletRequestBuilder createBuilder2 =
				MockMvcRequestBuilders.post("/v1/setCaptain/6")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(create2));
		mvc.perform(createBuilder2)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"success\":true")));

	}

}

