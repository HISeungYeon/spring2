package kr.or.ddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/book")
@Controller
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/list")
	public String list(Model model) {
		
		List<BookVO> bookVOList = this.bookService.list();
		log.info("bookVOList : " + bookVOList);
		
		//공통 약속
		model.addAttribute("bodyTitle", "도서 목록");
		model.addAttribute("bookVOList", bookVOList);
		
		return "book/list";
	}
	
	//요청URI : /book/detail?bookId=3
	//요청파라미터 : bookId=3
	// 1) 스프링에서 요청 파라미터를 매개변수로 받을 수 있다.
	// 요청 파라미터는 String타입. int형 매개변수로도 받을 수 있음 (자동 형변환 가능)
	// 매개변수 : String bookId / int bookId
	// 2) Map<String, String> Map <String, Object> 가능
	// 3) @ModelAttribute BookVO bookVO
	@GetMapping("/detail")
	public String detail(int bookId, Model model) {
		
		BookVO bookVO = this.bookService.detail(bookId);
		log.info("bookNo는???" + bookVO.toString());
		
		model.addAttribute("bookVO", bookVO);
		
		return "book/detail";
	}
	
	@PostMapping("/updatePost")
	public String updatePost(@ModelAttribute BookVO bookVO) {
		
		log.info("updatePost=>bookVO : " + bookVO.toString());
		
		int result = this.bookService.update(bookVO);
		
		if(result>0) { //업데이트 성공 -> 책 상세페이지(detail.jsp)로 이동
			return "redirect:/book/detail?bookId="+bookVO.getBookId();
		}else { //업데이트 실패 => 업데이트 뷰(update.jsp)로 페이지 이동
			return "redirect:/book/update?bookId"+bookVO.getBookId();
		}
		
	}
	
	@GetMapping("/insert")
	public String insert() {
		
		return "book/insert";
	}
	
	@PostMapping("/insert")
	public String insertPost(@ModelAttribute BookVO bookVO) {
		
		int result = this.bookService.insert(bookVO);
		log.info("result는???" + result);
		
		return "redirect:/book/detail?bookId="+bookVO.getBookId();
	}
	
}
