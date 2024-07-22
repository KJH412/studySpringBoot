package com.shinhan.firstzone;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; //타입 주의
import org.springframework.data.domain.Sort;

import com.shinhan.firstzone.repository.BoradRepository;
import com.shinhan.firstzone.vo.BoardEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest  //Junit(단위test)으로 test
public class BoardCRUDTest {  /* 단위테스트 @Test */
	
	@Autowired
	BoradRepository bRepo;
	
	@Test
	void f9() {
//		bRepo.jpdlTest1("요일", "홀수").forEach(b -> log.info(b));
//		bRepo.jpdlTest2("요일", "홀수").forEach(b -> log.info(b));
//		bRepo.jpdlTest3("요일", "홀수").forEach(b -> log.info(b));
//		bRepo.jpdlTest4("요일", "홀수").forEach(arr -> log.info(Arrays.toString(arr)));
		bRepo.jpdlTest5("%요일%", "%홀수%").forEach(b -> log.info(b));
	}
	
	//@Test
	void f8() {
//		Pageable pageable = PageRequest.of(0,  5); //PageNumber는 0부터 시작, size
//		Pageable pageable = PageRequest.of(0,  5, Sort.by("bno").descending());
		
		//여러 건 : new String[]{"bno"}
		Pageable pageable = PageRequest.of(0,  5, Sort.Direction.DESC, new String[]{"writer","bno"});
//		order by writer desc, bno desc limit ?,? (?부터 ?까지)
//		Pageable pageable = PageRequest.of(0,  5, Sort.Direction.DESC, "bno"); //한 건
		Page<BoardEntity> result = bRepo.findAll(pageable); //
		
		List<BoardEntity> blist = result.getContent();
		
		//paging정보
		log.info("getNumber:" + result.getNumber());
		log.info("getSize:" + result.getSize());
		log.info("getTotalElements:" + result.getTotalElements());
		log.info("getTotalPages:" + result.getTotalPages());
		blist.forEach(board -> log.info(board));
	}
	
	//@Test
	void f7() {
		//where bno > 10 order by bno desc ==> 24개 (11~35)//내 데이터 기준
		//0 : 35~30
		//1: 29~25
		//2: 24~19
		//3: ... 
		// ~11까지
		//10은 X. bno>10이 조건이기 때문에!
		Pageable pageable = PageRequest.of(0, 5); 
		//PageNumber는 0부터 시작, size (페이지 시작 번호, 페이지 최대 항목 수)
		//=> 0번째 5개 항목을 보여준다. (Desc이라 큰 수부터 보여줌)
		//PageRequest.of(1, 5) -> 1번째 5개 항목
		
		bRepo.findByBnoGreaterThanOrderByBnoDesc(5L, pageable).forEach(b->{ 
			log.info(b.toString());
		});
	}
	//@Test
	void f6() {
		bRepo.findByContentLikeAndBnoBetweenOrderByBnoDesc("짝", 1L, 15L).forEach(b->{ 
			log.info(b.toString());
		});
	}
	
	//@Test
	void f5() {
		bRepo.findByBnoBetween(10L, 15L).forEach(b->{ 
			log.info(b);
		});
	}
	
	//@Test
	void f4() {
		bRepo.findByTitleContaining("1").forEach(b->{ 
			log.info(b);
		});
	}
	
	//@Test
	void f3() {
		bRepo.findByTitleEndingWith("1").forEach(b->{ 
			log.info(b);
		});
	}
	
	//@Test
	void f2() {
		bRepo.findByTitleStartingWith("월").forEach(b->{ 
			log.info(b);
		});
	}
	
	//@Test
	void f1() {
		//findByWriter interface에 정의되어 있음
		bRepo.findByWriter("user1").forEach(b->{ //1건이 들어옴.
			log.info(b);
		});
	}
	//건수 확인
	//@Test
	void boardCount() {
		Long cnt = bRepo.count(); //select count(*) from t_board;
		log.info(cnt + "건");
	}
	
	//@Test
	void delete() {
		//deleteById() : interface에 작성한 pk를 받기 때문에 Long타입을 받는다.
		bRepo.deleteById(2L);
		//ifPresentOrElse: 있을 경우 or 없을 경우
		bRepo.findById(2L).ifPresentOrElse(b->{
			log.info(b);
		}, ()->{
			log.info("Not Found");
		});
	}
	
	//수정
	//@Test 
	void update() {
		//findById() : interface에 작성한 pk를 받기 때문에 Long타입을 받는다.
		bRepo.findById(1L).ifPresent(board -> { //기존 bno값(1)을 가져온 후 update를 진행함.
			log.info("before:" + board);
			board.setContent("~~~수정~~~~~");
			board.setTitle("springboot");
			board.setWriter("manager");
			BoardEntity updateBoard = bRepo.save(board); //update가 됨.
			log.info("after:" + board);
		});
	}
	
	//@Test
	void detail() {
		bRepo.findById(50L).ifPresentOrElse(board->{
			log.info(board);
		}, ()->{
			log.info("해당 데이터가 존재하지 않습니다.");
		});
	}
	
	//@Test
	void select() {
		bRepo.findAll().forEach(board ->{
			log.info(board);
		});
	}
	
	//@Test
	void insert() {
		//반복문 돌면서 BoradEntity를 10개 생성, insert가 10개 생성
		IntStream.rangeClosed(1, 10).forEach(i -> {
			BoardEntity entity = BoardEntity.builder()
								.title("월요일" + i)
								.content("비가 내립니다." + (i%2==0?"짝수":"홀수"))
								.writer("user"+ i%5)
								.build();
			bRepo.save(entity);
		});
	}
}
