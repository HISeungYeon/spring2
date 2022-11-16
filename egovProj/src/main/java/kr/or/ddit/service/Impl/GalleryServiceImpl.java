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
}
