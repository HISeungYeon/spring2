package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	private String memId;	//필수! username
	private String memPass;	// 필수! password
	private String memName;
	private String memRegno1;
	private String memRegno2;
	private Date memBir;
	private String memZip;
	private String memAdd1;
	private String memAdd2;
	private String memHometel;
	private String memComtel;
	private String memHp;
	private String memMail;
	private String memJob;
	private String memLike;
	private String memMemorial;
	private Date memMemorialday;
	private int memMileage;
	private String memDelete;
	private String enabled; //필수! enabled
	//1:N
	private List<MemberAuthVO> memberAuthVOList;

}
