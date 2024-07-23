package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shinhan.firstzone.repository.GuestBookRepository;
import com.shinhan.firstzone.service.GuestBookSerivce;
import com.shinhan.firstzone.vo2.GuestBookDTO;
import com.shinhan.firstzone.vo2.GuestBookEntity;
import com.shinhan.firstzone.vo2.QGuestBookEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class GuestBookTest {
	
	@Autowired
	GuestBookRepository gRepo;
	
	@Autowired 
	GuestBookSerivce guestBookSerivce;
	
	@Test
	void f5() { 
		String type = "t"; //t->title, c->content, w->writer, tc->title,content, tcw ->title,content,writer
		String keyword = "요일";
		
		//String[] arr = type.split("");  배열 자를 필요 없음
		//System.out.println(Arrays.toString(arr));
		BooleanBuilder builder = new BooleanBuilder();
		QGuestBookEntity entity = QGuestBookEntity.guestBookEntity;
		BooleanExpression expression = entity.gno.gt(0L); //gno > 0
		builder.and(expression); //select b from GuestBookEntity b where b.gno > 0
		
		BooleanBuilder builder2 = new BooleanBuilder();
		//포함된 문자를 찾아서 builder에 더한다.
		if(type.contains("t")) {
			builder2.or(entity.title.contains(keyword));
		}
		if(type.contains("c")) {
			builder2.or(entity.title.contains(keyword));
		}
		if(type.contains("w")) {
			builder2.or(entity.title.contains(keyword));
		}
		builder.and(builder2); //and (title like ? or content like ? or writer like ?)
		System.out.println(builder);
		gRepo.findAll(builder).forEach(aa -> {
			log.info(aa);
		});
		
		
	}
	
	//@Test
	void f4() { //가변 조회
		String aa = "요일";
		String bb = "사용자2";
		//querydsl
		BooleanBuilder builder = new BooleanBuilder();
		QGuestBookEntity book = QGuestBookEntity.guestBookEntity;
		//select * from GuestBookEntity
		builder.and(book.title.like("%" + aa + "%" )); //title은 칼럼명 , aa는 변수
		builder.and(book.writer.eq(bb));
		builder.and(book.gno.gt(0L));
		//where title like '%요일%' and writer= '작성자2' and gno > 0
		System.out.println(builder); //guestBookEntity.title like %요일% && guestBookEntity.writer = 사용자2 && guestBookEntity.gno > 0
		gRepo.findAll(builder).forEach(entity -> {
			log.info(entity);
		});;
		
	}
	
	//@Test
	void f3() {
		List<GuestBookDTO> list = guestBookSerivce.readAll();
		list.forEach(dto -> {
			log.info(dto);
		});
	}
	
	//@Test
	void f2() {
		//Entity -> DTO
		GuestBookEntity entity = gRepo.findById(1L).orElse(null); //없으면 null
		if(entity != null) {
			GuestBookDTO dto = GuestBookDTO.builder()
								.gno(entity.getGno())
								.title(entity.getTitle())
								.content(entity.getContent())
								.writer(entity.getWriter())
								.regDate(entity.getRegDate())
								.modDate(entity.getModDate())
								.build();
			log.info(dto);
		}
	}
	
	//@Test
	void f1() {
		//DTO -> Entity
		GuestBookDTO dto = GuestBookDTO.builder()
						.gno(1L)
						.title("타이틀")
						.content("내용")
						.writer("작성자")
						.build();
		
		GuestBookEntity entity = GuestBookEntity.builder()
								.title(dto.getTitle())
								.content(dto.getContent())
								.writer(dto.getWriter())
								.build();
		gRepo.save(entity);
	}
	
	/* (복습)등록일이 null인 data 조회하기 IsNull/Null메소드 */
	//@Test 
	void select5() {//gno가 10보다 크고, regdate가 null인 데이터 조회
		gRepo.findByRegDateIsNull4(10L).forEach(entity ->{
			log.info(entity);
		});
	}
	
	//@Test 
	void select4() {
		gRepo.findByRegDateIsNull3().forEach(entity ->{
			log.info(entity);
		});
	}
	
	//@Test 
	void select3() {
		gRepo.findByRegDateIsNull2().forEach(entity ->{
			log.info(entity);
		});
	}
	
	//@Test
	void select2() {
		gRepo.findByRegDateIsNull().forEach(entity -> {
			log.info(entity);
		});
	}
	
	//@Test
	void select() {
		//new Consumer<GuestBookEntity>() {} 대신 람다표현식(화살표함수)
		gRepo.findAll().forEach(entity -> {
			log.info(entity);
		});
	}
	
	/* insert문 */
	//@Test 
	void insert() {
		IntStream.rangeClosed(6, 10).forEach(i -> {
			//객체 생성 new GuestBookEntity보다 유지보수하기 좋은 builder()를 사용
			GuestBookEntity entity = GuestBookEntity.builder() //builder사용-> AllArgs생성자 생성
									.title("화요일%%"+i)
									.content("배고파##")
									.writer("사용자"+i)
									.build();
			gRepo.save(entity);
		
		});
	}
}