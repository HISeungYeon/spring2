package kr.or.ddit.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.service.GalleryService;
import kr.or.ddit.vo.BookAuthVO;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
@RequestMapping("/gallery")
@Controller
public class GalleryController {

	@Autowired
	GalleryService galleryService;

	@GetMapping("/list")
	public String insert(Model model, @ModelAttribute BookVO bookVO, @ModelAttribute BookAuthVO bookAuthVO) {

//		bookVO.setBookId(3);

		bookVO = this.galleryService.list(bookVO);
		log.info("bookVO ?? " + bookVO);

//		List<BookAuthVO> bookAuthVOList = bookVO.getBookAuthVOList();
//		for(int i=0;i<bookAuthVOList.size();i++) {
//			log.info("모였더라 ?? " + bookAuthVOList.get(i).getSeq());
//		}

		// 공통 약속
		model.addAttribute("bodyTitle", "이미지 목록");
		model.addAttribute("bookVO", bookVO);

		return "gallery/list";
	}

	// 도서 목록 가져와서 select에 추가하기
	// json 데이터로 리턴
	@ResponseBody
	@GetMapping("/bookList")
	public List<BookVO> bookList() {
		List<BookVO> bookVOList = this.galleryService.bookList();

		log.info("bookVOList : " + bookVOList);

		return bookVOList;
	}

	// 방식 : post
	// 첨부이미지를 변경함
	@ResponseBody
	@PostMapping("/updatePost")
	public BookAuthVO updatePost(MultipartFile[] uploadFile, @ModelAttribute BookAuthVO bookAuthVO, HttpServletRequest request) {
		log.info("uploadFile : " + uploadFile + " bookAuthVO : " + bookAuthVO);

		// 업로드 폴더 설정
		String uploadFolder = "C:\\eGovFrameDev-3.10.0-64bit\\workspace\\egovProj\\src\\main\\webapp\\resources\\upload";

		// 연월일 폴더 생성
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("upload Path : " + uploadPath);

		// 만약 연/월/일 해당 폴더가 없으면 생성
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		String uploadFileName = "";

		// 파일 배열로부터 파일 하나씩 가져와보쟈
		for (MultipartFile multipartFile : uploadFile) {
			log.info("-----------------");
			log.info("upload File Name : " + multipartFile.getOriginalFilename());
			log.info("upload File Size : " + multipartFile.getSize());
			log.info("누구야 ㅡㅡ : " + request.getRemoteAddr());

			uploadFileName = multipartFile.getOriginalFilename();

			// 같은 날 같은 이미지 업로드 시 파일 중복 방지 시작
			// java.util.UUID => 랜덤값 생성
			UUID uuid = UUID.randomUUID();
			// 원래의 파일 이름과 구분하기 위해 _를 붙여보쟈
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			// 같은 날 같은 이미지 업로드 시 파일 중복 방지 끝

			// File객체 설계(복잡할 대상 경로, 파일명)
			// uploadPath :
			// C:\\eGovFrameDev-3.10.0-64bit\\workspace\\egovProj\\src\\main\\webapp\\resources\\upload\2022\11\16
			File saveFile = new File(uploadPath, uploadFileName);

			try {
				// 파일 복사 실행
				multipartFile.transferTo(saveFile);

				// 썸네일 처리
				// 이미지인지 체킹
				if (checkImageType(saveFile)) { // 이미지라면
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					// 썸네일 생성
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}

				String filename = "/" + getFolder().replace("\\", "/") + "/" + uploadFileName;
				log.info("filename??? " + filename);
				bookAuthVO.setFilename(filename);

				int result = this.galleryService.update(bookAuthVO);
				log.info("업데이트 됐나욤?? " + result);

				return bookAuthVO;

			} catch (IllegalStateException e) {
				log.error(e.getMessage());
				return null;
			} catch (IOException e) {
				log.error(e.getMessage());
				return null;
			} // end try
		} // end for

		return null;
	}

	// 연/월/일 폴더 생성
	public String getFolder() {
		// 2022-11-16 형식 (format) 지정
		// 간단한 날짜 형식
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 날짜 객체 생성
		Date date = new Date();
		// 2022-11-16
		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	// 이미지인지 판단. 썸네일은 이미지만 가능하므로,,
	public boolean checkImageType(File file) {

		// MINE 타입 알아냄..jpeg/.jpg의 MIME타입 : image/jpeg
		String contentType;

		try {
			contentType = Files.probeContentType(file.toPath());
			log.info("contentType : " + contentType);
			// image/jpeg는 image로 시작함. -> true
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 이 파일이 이미지가 아닐 경우
		return false;
	}

	/**
	 * 이미지 삭제
		@RequestBody : 요청 파라미터 타입(contentType = 'application/json;charset=utf-8')이 
					json일 때 Map 또는 VO로 받음
		@ResponseBody : json데이터로 리턴할 때 사용
	 */
	@ResponseBody
	@PostMapping("/deletePost")
	public Map<String, String> deletePost(@RequestBody BookAuthVO bookAuthVO) {
		log.info("bookAuthVO?? " + bookAuthVO);

		Map<String, String> map = new HashMap<String, String>();

		int result = this.galleryService.delete(bookAuthVO);
		map.put("result", result+"");

		log.info("삭제 되었나욤??" + result);

		return map;

	}
	
	/**
	 * 이미지 다중 등록
	 */
	@GetMapping("/regist")
	public String regist(Model model) {
		
		//공통 약속
		model.addAttribute("bodyTitle", "이미지 등록");
		
		return "gallery/regist";
	}
	
	
	/**
	 * 
	 * @param bookVO
	 * @return
	 */
	@ResponseBody
	@PostMapping("/searchBook")
	public List<BookVO> searchBook(@RequestBody BookVO bookVO) {
		log.info("도서 검색 들어오나! " + bookVO);
		
		List<BookVO> bookVOList = this.galleryService.searchBook(bookVO);
		log.info("bookVOList" + bookVOList);
		
		return bookVOList;
	}
	
	/*
	 * 요청파라미터 : uploadFile[], bookId => 폼으로 오므로 RequestBody(json으로 올때만!)는 안씀.
	 */
	@ResponseBody
	@PostMapping("/upladAjaxAction")
	public Map<String, String> upladAjaxAction(MultipartFile[] uploadFile, @RequestParam String bookId, HttpServletRequest request){
		log.info("uploadFile : " + uploadFile);
	      
	      //업로드 폴더 설정
	      String uploadFolder = 
	            "C:\\eGovFrameDev-3.10.0-64bit\\workspace\\egovProj\\src\\main\\webapp\\resources\\upload";
	      
	      //연월일 폴더 생성
	      File uploadPath = new File(uploadFolder,getFolder());
	      log.info("upload Path : " + uploadPath);
	      
	      //만약 연/월/일 해당 폴더가 없으면 생성
	      if(uploadPath.exists()==false) {
	         uploadPath.mkdirs();
	      }
	      
	      //원래 파일명
	      String uploadFileName = "";
	      List<BookAuthVO> attachVOList = new ArrayList<BookAuthVO>();
	      
	      int seq = this.galleryService.getSeq(bookId);
	      
	      //파일 배열로부터 파일을 하나씩 가져와보자.
	      //MultipartFile[] uploadFile
	      for(MultipartFile multipartFile : uploadFile) {
	    	 BookAuthVO bookAuthVO = new BookAuthVO();
	         log.info("-----------------");
	         log.info("upload File Name : " + multipartFile.getOriginalFilename());
	         log.info("upload File Size :" + multipartFile.getSize());
	         //개똥이.jpg
	         uploadFileName = multipartFile.getOriginalFilename();
	         
	         //같은 날 같은 이미지 업로드 시 파일 중복 방지 시작----------------
	         //java.util.UUID => 랜덤값 생성
	         UUID uuid = UUID.randomUUID();
	         //원래의 파일 이름과 구분하기 위해 _를 붙임(sdafjasdlfksadj_개똥이.jpg)
	         uploadFileName = uuid.toString() + "_" + uploadFileName;
	         //같은 날 같은 이미지 업로드 시 파일 중복 방지 끝----------------
	         
	         //File객체 설계(복사할 대상 경로, 파일명)
	         //uploadPath : C:\\eGovFrameDev-3.10.0-64bit\\workspace
	         //             \\egovProj\\src\\main\\webapp\\resources\\upload\\2022\\11\\16
	         File saveFile = new File(uploadPath, uploadFileName);
	         
	         try {
	            //파일 복사 실행
	            multipartFile.transferTo(saveFile);
	            
	            //썸네일 처리
	            //이미지인지 체킹
	            if(checkImageType(saveFile)) {//이미지라면
	               FileOutputStream thumbnail = new FileOutputStream(
	                     new File(uploadPath,"s_"+uploadFileName)
	                     );
	               //썸네일 생성
	               Thumbnailator.createThumbnail(multipartFile.getInputStream(),
	                     thumbnail,100,100);
	               thumbnail.close();
	            }
	            
	            String filename = "/" + getFolder().replace("\\", "/") + "/" + uploadFileName;
	            log.info("filename : " + filename);
	            
	            
	            bookAuthVO.setUserNo(bookId);
	            bookAuthVO.setSeq(seq++);
	            bookAuthVO.setFilename(filename);
	            bookAuthVO.setFilesize(multipartFile.getSize() + "");
	            
	            attachVOList.add(bookAuthVO);
	            
	         }catch (IllegalStateException e) {
	            log.error(e.getMessage());
	            return null;
	         }catch(IOException e) {
	            log.error(e.getMessage());
	            return null;
	         }//end try
	      }//end for
	      
	      int rslt = this.galleryService.insertAttach(attachVOList);
	      
	      Map<String,String> map = new HashMap<String, String>();
	  	    map.put("bookId", bookId);
	  	    map.put("result", rslt+"");
          
	  	    return map;
		
	}

}
