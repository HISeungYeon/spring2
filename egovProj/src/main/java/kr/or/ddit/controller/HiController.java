package kr.or.ddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.service.HiService;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/hi")
@Controller
public class HiController {

	@Autowired
	HiService hiService;
	
	@GetMapping("/list")
	public String list(Model model, @ModelAttribute BookVO bookVO) {
		
		bookVO.setBookId(3);
		
		bookVO = this.hiService.list(bookVO);
		
		log.info("BookVO 오나욤?? " + bookVO);
		
		model.addAttribute("bookVO", bookVO);
		
		
		return "hi/list";
	}
	
}
