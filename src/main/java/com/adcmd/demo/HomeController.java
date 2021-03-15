package com.adcmd.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home()
	{
		System.out.println("Chillax' Home Page Controller..!");
		return "home.jsp";
	}
}
