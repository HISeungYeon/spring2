package kr.or.ddit.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.HiMapper;
import kr.or.ddit.service.HiService;
import kr.or.ddit.vo.BookVO;

@Service
public class HiServiceImpl implements HiService {
	
	@Autowired
	HiMapper hiMapper;
	
	@Override
	public BookVO list(BookVO bookVO) {
		return this.hiMapper.list(bookVO);
	}
	
	
}
