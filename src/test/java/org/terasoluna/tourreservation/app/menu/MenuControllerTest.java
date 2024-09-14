package org.terasoluna.tourreservation.app.menu;

import org.junit.jupiter.api.Test;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class MenuControllerTest {

	MockMvc mockMvc;

	/**
	 * Check MenuController
	 */
	@Test
	void init() throws Exception {
		MenuController controller = new MenuController();
		mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/")).andExpect(view().name("menu/menu"));
	}

}