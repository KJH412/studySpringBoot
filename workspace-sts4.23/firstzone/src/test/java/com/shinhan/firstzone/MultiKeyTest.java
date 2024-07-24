package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository3.MultiKeyARepository;
import com.shinhan.firstzone.repository3.MultiKeyBRepository;
import com.shinhan.firstzone.vo3.MultiKeyA;
import com.shinhan.firstzone.vo3.MultiKeyAClass;
import com.shinhan.firstzone.vo3.MultiKeyB;
import com.shinhan.firstzone.vo3.MultiKeyBClass;

import jakarta.persistence.EmbeddedId;

@SpringBootTest
public class MultiKeyTest {
	
	@Autowired
	MultiKeyARepository aRepo;
	
	@Autowired
	MultiKeyBRepository bRepo;
	
	@Test
	void selectB() {
		
		/*
		아이디 한개id1(1)만 찾아도 sql쿼리문에서 아이디 두개를 찾음
		"... where (mkb1_0.id1,mkb1_0.id2)=(?,?)" 
		*/
		MultiKeyB id = MultiKeyB.builder()
						.id1(1).id2(2)
						.build();
		bRepo.findByMultib(id).forEach(data->{
			System.out.println(data);
		});
	}
	
	//@Test
	void insertB() {
		//@EmbeddedId MultiKeyB id;
		MultiKeyB id = MultiKeyB.builder()
							.id1(2).id2(3)
							.build();
		MultiKeyBClass b = MultiKeyBClass.builder()
				.multib(id)
				.productName("커피")
				.price(100)
				.build();
		bRepo.save(b);
		
	}
	
	//@Test
	void selectA() {		
		aRepo.findById1(10L).forEach(aa->System.out.println(aa));
	}
	
	//id 2개 조회
	//@Test
//	void selectA() {
//		MultiKeyA aKey = MultiKeyA.builder().id1(10).id2(20).build();
//		aRepo.findById(aKey).ifPresent(a->{
//			System.out.println(a);
//		});
//	}
	
	//@Test
	void insertA() {
		MultiKeyAClass a = MultiKeyAClass.builder()
				.id1(10)
				.id2(30)
				.productName("노트북A")
				.price(100)
				.build();
		aRepo.save(a);
		
		
	}
}