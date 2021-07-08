package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WelcomeController  {


	@GetMapping("/404")
	public String notFoundPage() {
		return "404";
	}

	@GetMapping("/403")
	public String accessError() {
		return "403";
	}

	@GetMapping("/500")
	public String internalError() {
		return "500";
	}

	@GetMapping("/success")
	@ResponseBody
	public String success(){
		return "认证成功,进入success成功";
	}

	@GetMapping(value = "/user/login")
	private String loginPage(){
		return "login";
	}

	@GetMapping(value = "/person")
	public String personPage(){
		return "person";
	}

	@GetMapping(value = "/admin/index")
	public String adminPage(){
		return "admin";
	}


	@GetMapping(value = "/index")
	public String index(){
		return "index";
	}

	@GetMapping(value = "webSocket")
	public String webSocket(){
		return "webSocket";
	}

}
