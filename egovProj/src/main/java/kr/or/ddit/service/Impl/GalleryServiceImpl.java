package kr.or.ddit.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.BookMapper;
import kr.or.ddit.mapper.GalleryMapper;
import kr.or.ddit.service.BookService;
import kr.or.ddit.service.GalleryService;
import kr.or.ddit.vo.BookAuthVO;
import kr.or.ddit.vo.BookVO;

@Service
public class GalleryServiceImpl implements GalleryService {
	
	@Autowired
	GalleryMapper galleryMapper;
	
	//이미지 목록
	@Override
	public BookVO list(BookVO bookVO) {
		return this.galleryMapper.list(bookVO);
	}
	
	//도서목록가져오기?
	@Override
	public List<BookVO> bookList(){
		return this.galleryMapper.bookList();
	}
	
	//이미지 수정
	@Override
	public int update(BookAuthVO bookAuthVO) {
		return this.galleryMapper.update(bookAuthVO);
	}
	
	//이미지 삭제
	@Override
	public int delete(BookAuthVO bookAuthVO) {
		return this.galleryMapper.delete(bookAuthVO);
	}
	
	//도서 검색
	@Override
	public List<BookVO> searchBook(BookVO bookVO) {
		return this.galleryMapper.searchBook(bookVO);
	}
	
	//이미지 등록
	@Override
	public int insertAttach(List<BookAuthVO> bookAuthVOList) {
		return this.galleryMapper.insertAttach(bookAuthVOList);
	}
	
	//책의 이미지인 ATTACH 테이블의 다음 seq번호 가져오기
	@Override
	public int getSeq (String bookId) {
		return this.galleryMapper.getSeq(bookId);
	}
}
