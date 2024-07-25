package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository3.PhoneVORepository;
import com.shinhan.firstzone.repository3.PhoneVORepository2;
import com.shinhan.firstzone.repository3.UserVO3Repository;
import com.shinhan.firstzone.repository3.UserVORepository;
import com.shinhan.firstzone.vo3.UserCellPhoneVO;
import com.shinhan.firstzone.vo3.UserCellPhoneVO2;
import com.shinhan.firstzone.vo3.UserCellPhoneVO3;
import com.shinhan.firstzone.vo3.UserVO;
import com.shinhan.firstzone.vo3.UserVO2;
import com.shinhan.firstzone.vo3.UserVO3;

@SpringBootTest
public class OneToOneTest {
	
	@Autowired
	UserVORepository uRepo;
	
	@Autowired
	PhoneVORepository pRepo;
	
	@Autowired
	PhoneVORepository2 pRepo2;
	
	@Autowired
	UserVO3Repository user3Repo;
	
	
	//@Test  //수정
	void f3() {
		UserCellPhoneVO3 phone = UserCellPhoneVO3.builder()
				.phoneNumber("1234")
				.model("aa")
				.build();
		UserVO3 user = UserVO3.builder()
							.userid("옥")
							.username("주현")
							.phone(phone) //phone 양방향
							.build();
		phone.setUser3(user); //user 양방향
		user3Repo.save(user);
	}
	
	//@Test
	void f2() {
		UserVO2 user = UserVO2.builder()
				.userid("coffee")
				.username("컴포즈")
				.build();
		UserCellPhoneVO2 phone = UserCellPhoneVO2.builder()
								.phoneNumber("010-1234-4567")
								.model("갤럭시")
								.user(user)
								.build();
		pRepo2.save(phone);
	}
	
	//@Test
	void f1() {
		//phone을 먼저 저장한 후 user를 저장한다.
		//tbl_usercellphone테이블에 먼저 저장된 후 => tbl_user테이블에 저장
		//phone 저장
		UserCellPhoneVO phone = UserCellPhoneVO.builder()
				.phoneNumber("010-1234-4567")
				.model("갤럭시")
				.build();
		UserCellPhoneVO newphone = pRepo.save(phone);
		//user저장
		UserVO uservo = UserVO.builder()
				.userid("testid")
				.username("jung")
				.phone(newphone)
				.build();
		uRepo.save(uservo);
	}
}
