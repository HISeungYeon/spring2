package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.BookVO;

public interface BookMapper {
	
	//도서목록
	public List<BookVO> list();
	
	//도서상세
	public BookVO detail(int bookId);
	
	//도서 수정
	public int update(BookVO bookVO);
	
	//도서 추가 
	public int insert(BookVO bookVO);
}
