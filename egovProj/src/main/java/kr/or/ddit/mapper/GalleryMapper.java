package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.BookAuthVO;
import kr.or.ddit.vo.BookVO;

public interface GalleryMapper {
	
	//이미지 리스트
	public BookVO list(BookVO bookVO);
	
	//도서목록가져오기?
	public List<BookVO> bookList();
	
	//이미지 업데이트
	public int update(BookAuthVO bookAuthVO);
	
	//이미지 삭제
	public int delete(BookAuthVO bookAuthVO);
	
	//도서 검색
	public List<BookVO> searchBook(BookVO bookVO);
	
	//이미지 등록
	public int insertAttach(List<BookAuthVO> bookAuthVOList);
	
	//책의 이미지인 ATTACH 테이블의 다음 seq번호 가져오기
	public int getSeq(String bookId);
}
