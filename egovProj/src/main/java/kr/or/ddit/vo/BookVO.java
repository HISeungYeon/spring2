package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.Data;

public class BookVO {
	private int bookId;
	private String title;
	private String category;
	@NumberFormat(style = Style.NUMBER)
	private int price;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date insertDate;
	private String content;
	//펌부파일 테이블(1:N)관계
	private List<BookAuthVO> bookAuthVOList;
	
	public BookVO() {}
	
	public List<BookAuthVO> getBookAuthVOList() {
		return bookAuthVOList;
	}

	public void setBookAuthVOList(List<BookAuthVO> bookAuthVOList) {
		this.bookAuthVOList = bookAuthVOList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	@Override
	public String toString() {
		return "BookVO [bookId=" + bookId + ", title=" + title + ", category=" + category + ", price=" + price
				+ ", insertDate=" + insertDate + ", content=" + content + ", bookAuthVOList=" + bookAuthVOList + "]";
	}


	
}
