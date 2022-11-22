package kr.or.ddit.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CommonController {
	//스프링 시큐리티 접근 권한 
	//접근이 불가능한 페이지라면 접근 거부 처리가 되어 accessError.jsp를 forwarding함.
	@RequestMapping("/security/accessError")
	public String accessDenied(Authentication auth, Model model) {
		log.info("access Denied : " + auth);
		
		model.addAttribute("msg", "Access Denied");
		
		return "security/accessError";
	}
	
	//기본 로그인 페이지가 아닌 사용자가 직접 정의한 로그인 페이지를 사용함
	//요청 URI : /security/login
	@RequestMapping("/security/login")
	public String loginForm(String error, String logout, Model model) {
		// 시큐리티에서 DB에 없으면 error를 보내고 없으면 null을 보내냐 보댜 ㅇㅁㅇ
		log.info("error : " + error);
		log.info("logout : " + logout);
		
		//오류 메세지 
		if(error != null) {
			model.addAttribute("error", "Login Error!!!");
		}
		
		//로그아웃 메세지 
		if(logout != null) {
			model.addAttribute("logout", "Logout!!!");
		}
		
		
		return "security/loginForm";
	}
	
}
