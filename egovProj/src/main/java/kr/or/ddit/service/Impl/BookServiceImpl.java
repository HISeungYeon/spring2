package kr.or.ddit.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.BookMapper;
import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	BookMapper bookMapper;
	
	//도서목록
	@Override
	public List<BookVO> list() {
		return this.bookMapper.list();
	}
	
	//도서상세
	@Override
	public BookVO detail(int bookId) {
		return this.bookMapper.detail(bookId);
	}
	
	//도서 수정
	@Override
	public int update(BookVO bookVO) {
		
		log.info("before bookVO : " + bookVO);
		
		//merge into문 사용
		//update(bookVO) -> insert(bookVO)
		int result = this.bookMapper.insert(bookVO);
		//bookId를 확인해보쟈
		log.info("after bookVO : " + bookVO);
		
		return result;
	}
	
	//도서 추가
	@Override
	public int insert(BookVO bookVO) {
		return this.bookMapper.insert(bookVO);
	}
	
}
