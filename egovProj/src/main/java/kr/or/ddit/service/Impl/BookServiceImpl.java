package kr.or.ddit.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.BookMapper;
import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.BookVO;

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
		return this.bookMapper.update(bookVO);
	}
	
	//도서 추가
	@Override
	public int insert(BookVO bookVO) {
		return this.bookMapper.insert(bookVO);
	}
	
}
