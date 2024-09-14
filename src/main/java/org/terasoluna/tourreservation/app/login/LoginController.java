/*
 * Copyright(c) 2013 NTT DATA Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.tourreservation.app.login;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static org.springframework.security.web.WebAttributes.AUTHENTICATION_EXCEPTION;

@Controller
@RequestMapping("/login")
public class LoginController {

	/**
	 * Shows the login view.
	 * @return
	 */
	@GetMapping
	public String loginForm(
			@SessionAttribute(name = AUTHENTICATION_EXCEPTION, required = false) AuthenticationException error,
			Model model) {
		if (error != null) {
			model.addAttribute("error", error.getMessage());
		}
		return "login/form";
	}

}
