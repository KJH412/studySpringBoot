package com.shinhan.firstzone;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.repository.ProfileRepository;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo2.MemberRole;
import com.shinhan.firstzone.vo2.ProfileEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class ManyToOneTest {

	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ProfileRepository pRepo;
	
	//@Test
	void getProfileCount() {
		pRepo.getProfileCount().forEach(arr->{
			log.info(Arrays.toString(arr));
			log.info("mid:" + arr[0] + ", profile개수:" + arr[1] );
			log.info("---------------------------------");
			
		});
	}
	
	@Test
	void getProfile() {
		//배열은 toString으로 출력. 그냥 찍으면 주소가 출력된다.
		pRepo.getProfile("user3").forEach(arr->{
			log.info(Arrays.toString(arr));
			log.info("이름:" + arr[0] + ", 파일이름:" + arr[1] );
			log.info("---------------------------------");
			
		});
	}
	
	//@Test
	void selectByCurrent() {
		pRepo.findByCurrentYnTrue().forEach(p->log.info(p));
	}
	
	//특정 Member의 Profile정보 조회
	//from t_profile where member_mid = 'user5' and currentYn = 1
	//@Test
	void selectByMember() {
		MemberEntity member = MemberEntity.builder().mid("user5").build();
		//interface에 함수 정의
		pRepo.findByMemberAndCurrentYnTrue(member).forEach(p->{
			log.info(p);
		});
	}
	
	//PK인 fno=1인 Profile 정보 조회
	//@Test
	void selectByFno() {
		pRepo.findById(1L).ifPresentOrElse(p ->{
			System.out.println("파일이름:"+p.getFname());
			System.out.println(p.isCurrentYn()?"현재프로파일임":"과거프로파일임"); //Boolean타입은 get이 아니고 is
			System.out.println("이름:" + p.getMember().getMname()); //Member에 들어가서 필드에 접근 할 수 있다. (모든 필드를 출력하고싶으면 getMember().toString()하면 됨.)
			System.out.println("이름:"+ p.getMember().getMrole().name());
		}, ()->{
			System.out.println("존재하지 않습니다.");
		});
	}
	
	//조회
	//@Test
	void selectProfile() {
		mRepo.findAll().forEach(p->{
			log.info(p);
		});
	}
	
	//해당 멤버id 정보 조회
	//@Test
	void selectByMid() {
		mRepo.findById("user5").ifPresentOrElse(m->{
			log.info(m);
		},()->{
			log.info("존재하지 않는 Member입니다.");
		});
	}
	
	//@Test
	void profileInsert() {
		MemberEntity member = MemberEntity.builder().mid("user5").build();
		IntStream.rangeClosed(1, 10).forEach(i->{
			ProfileEntity profile = ProfileEntity.builder()
									.fname("face-" + i)
									.currentYn(i==5? true : false)
									.member(member)
									.build();
			pRepo.save(profile);
		});
		
	}
	
	//1.Member insert
	//@Test
	void memberInsert() {
		IntStream.rangeClosed(1, 5).forEach(i -> { //보통 규칙이 없는데 roop 돌리진 않음.
			MemberEntity member = MemberEntity.builder()
										.mid(i==1? "king" : "user" + i)
										.mname("김민영" + i)
										.mpassword("1234")
										.mrole(i==1? MemberRole.MANAGER : MemberRole.USER)
										.build();
			mRepo.save(member);
			
		});
	}
	
	//2.
}