package kr.or.ddit.mapper;

import kr.or.ddit.vo.MemberVO;

public interface MemberMapper {
	//회원 로그인 확인
	public MemberVO read(String memId);
	
	
}
