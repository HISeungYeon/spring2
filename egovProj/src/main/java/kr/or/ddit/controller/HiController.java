package kr.or.ddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/hi")
@Controller
public class HiController {

	@GetMapping("/list")
	public String list() {
		
		
		
		return "hi/list";
	}
	
}
