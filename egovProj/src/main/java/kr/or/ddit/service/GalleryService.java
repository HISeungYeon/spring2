package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.BookAuthVO;
import kr.or.ddit.vo.BookVO;

public interface GalleryService {

	//이미지 리스트
	public BookVO list(BookVO bookVO);

	public List<BookVO> bookList();

	//이미지 수정
	public int update(BookAuthVO bookAuthVO);

	//이미지 삭제
	public int delete(BookAuthVO bookAuthVO);

	//도서 검색
	public List<BookVO> searchBook(BookVO bookVO);

	
}
