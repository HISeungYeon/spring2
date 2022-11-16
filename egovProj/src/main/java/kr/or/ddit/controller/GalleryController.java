package kr.or.ddit.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
//		model.addAttribute("bookAuthVOList", bookAuthVOList);

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
	public BookAuthVO updatePost(MultipartFile[] uploadFile, @ModelAttribute BookAuthVO bookAuthVO) {
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

	@ResponseBody
	@PostMapping("/deletePost")
	public Map<String, String> deletePost(@ModelAttribute BookAuthVO bookAuthVO) {
		log.info("bookAuthVO?? " + bookAuthVO);

		Map<String, String> map = new HashMap<String, String>();

		map.put("result", "1");

		int result = this.galleryService.delete(bookAuthVO);
		log.info("삭제 되었나욤??" + result);

		return map;

	}

}
