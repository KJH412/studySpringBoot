package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.repository3.FreeBoardRepository;
import com.shinhan.firstzone.repository3.FreeReplyRepository;
import com.shinhan.firstzone.vo3.FreeBoardEntity;
import com.shinhan.firstzone.vo3.FreeReplyEntity;
import com.shinhan.firstzone.vo3.QFreeBoardEntity;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BiDirectionTest {
	
	@Autowired
	FreeBoardRepository boardRepo;
	@Autowired
	FreeReplyRepository replyRepo;
	
	@Test
	void queryDslTest() {
		String type = "tcw";
		String keyword = "맑음";
		BooleanBuilder builder = new BooleanBuilder();
		QFreeBoardEntity board =QFreeBoardEntity.freeBoardEntity;
		//gt: greater than 
		builder.and(board.bno.gt(0L));
		
		BooleanBuilder builder2 = new BooleanBuilder();
		
		if(type.contains("t")) {
			builder2.or(board.title.like("%"+keyword+"%"));
		}
		if(type.contains("c")) {
			builder2.or(board.title.like("%"+keyword+"%"));
		}
		if(type.contains("w")) {
			builder2.or(board.title.like("%"+keyword+"%"));
		}
		builder.and(builder2);
		System.out.println(builder);
		Pageable page = PageRequest.of(0, 10);
		Page<FreeBoardEntity> result = boardRepo.findAll(builder, page);
		//findAll(): org.springframework.data.querydsl.QuerydslPredicateExecutor.findAll (
		//interface에 QuerydslPredicateExecutor있어야함.
		List<FreeBoardEntity> list = result.getContent();
		list.forEach(b->{ System.out.println(b); });
		System.out.println("getSize:" + result.getSize());
		System.out.println("getTotalPages:" + result.getTotalPages());
		System.out.println("getTotalElements:" + result.getTotalElements());
		System.out.println("nextPageable:" + result.nextPageable());
	}
	
//	@Test
	void join2() {
		//N:1에서 TEST하는 중
		//Reply select....Reply가 참조하는 board가 5가지라면 Reply select 1개, board select문 5개
		//N+1 : 내가 조회하려는 것 보다 더 많이 조회됨.
//		replyRepo.findAllWithReplyUsingJoin().forEach(b->System.out.println(b));
		//N+1문제를 해결한다.(join fetch사용)
		replyRepo.findAllWithReplyUsingJoinFetch().forEach(b->System.out.println(b));
	}
	
	//@Test
	void join1() {
		boardRepo.findAllWithReplyUsingJoin().forEach(b->System.out.println(b));
//		boardRepo.findAllWithReplyUsingJoinFetch().forEach(b->System.out.println(b));
	}
	
	
	//조건 조회 bno>=10 and bno<=20, paging추가, sort추가
	//@Transactional
	//@Test
	void selectBoard2() {
		// 조건 조회 (bno >= 1 and bno <= 10, paging 추가, sort 추가)
		// 2번째 페이지 5건
		Pageable pageable = PageRequest.of(1, 5, Sort.Direction.DESC, "bno"); 
		
		// where bno between 1 and 10 order by bno desc
		// 페이지 정보도 조회하는 경우
		Page<FreeBoardEntity> result = boardRepo.findByBnoBetween(1L, 10L, pageable);
		System.out.println("getNumber : " + result.getNumber());
		System.out.println("getSize : " + result.getSize());
		System.out.println("getTotalElements : " + result.getTotalElements());
		System.out.println("getTotalPages : " + result.getTotalPages());
		System.out.println("getSort : " + result.getSort());
		System.out.println("getPageable : " + result.getPageable().next());
		
		result.getContent().forEach(board -> {
			System.out.println(board);
			System.out.println(board.getReplies().size() + "건");
		});
		
		/*
		boardRepo.findByBnoBetween(1L, 10L, pageable).forEach(board -> {
			System.out.println(board);
			System.out.println(board.getReplies().size() + "건");
		});
		*/
	}
	//BoardTitle, Reply 건수
	//@Test
	void selectBoardReply() {
		boardRepo.getBoardReplyCount2().forEach(arr -> {
			System.out.println(Arrays.toString(arr));
		});;
	}
	
//	@Transactional 
//	@Test
	void selectBoard() {
		boardRepo.findAll().forEach(board->{
			System.out.println(board);
			System.out.println("댓글건수" + board.getReplies().size());
		});
	}
	
	//조회(Board번호를 알고 댓글들의 정보조회)
	//@Transactional //board의 fetch타입이 LAZY이고, toString에서 exclude되어있기 때문에 선언해줌.
	//@Test
	void selectByReply() {
		//보드 번호 5의 댓글 조회
		FreeBoardEntity board = FreeBoardEntity.builder().bno(5L).build();
		replyRepo.findByBoard(board).forEach(reply->{
			System.out.println(reply);
			System.out.println(reply.getBoard());
		});
	}
	
	//댓글수정
	@Test
	void updateReply() {
		//board_bno가 null인 것
		FreeBoardEntity board = FreeBoardEntity.builder().bno(30L).build();
		//Long[] arr = {14L, 15L, 16L};
		//List<Long> idList = Arrays.asList(arr);
		//여러개 bno받기
		replyRepo.findAllById(Arrays.asList(14L, 15L, 16L)).forEach(reply->{
			reply.setBoard(board);
			//replyRepo.save(null);
			replyRepo.save(reply);
		});
		 
	}
	
	//댓글 삽입
	//@Test
	void insertReply() {
//		FreeBoardEntity board = boardRepo.findById(4L).get(); //bno=4번 가져옴
		FreeBoardEntity board = FreeBoardEntity.builder().bno(4L).build();
		FreeReplyEntity reply = FreeReplyEntity.builder()
				.reply("점심메뉴")
				.replyer("user1")
				.board(board) 
				.build();
		replyRepo.save(reply);
	}
	
	//특정 board의 댓글 삽입
	//@Test
	void insertBoard2() {
		//bno=1이 있다면
		 boardRepo.findById(3L).ifPresent(board->{
			List<FreeReplyEntity> replyList = board.getReplies();
			//댓글 삽입
			IntStream.rangeClosed(1, 3).forEach(i->{
				FreeReplyEntity reply = FreeReplyEntity.builder()
						.reply("맛집~~" + i)
						.replyer("user" + i%2)
						.board(board) //부모를 참조한다.
						.build();
				replyList.add(reply);
			});
			boardRepo.save(board);
		 });
	}
	
	//@Test
	void insertBoard() {
		//30건
		IntStream.rangeClosed(1, 30).forEach(i->{ //댓글 제외 보드 생성
			FreeBoardEntity entity = FreeBoardEntity.builder()
									.title("수요일"+i)
									.content("오늘 날씨는" + (i%2==0? "맑음":"비"))
									.writer("user" + i%5)
									.build();
			boardRepo.save(entity);
		});
	}
}
