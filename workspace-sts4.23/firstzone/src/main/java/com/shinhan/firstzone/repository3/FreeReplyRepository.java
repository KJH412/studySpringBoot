package com.shinhan.firstzone.repository3;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.shinhan.firstzone.vo3.FreeBoardEntity;
import com.shinhan.firstzone.vo3.FreeReplyEntity;

//댓글
public interface FreeReplyRepository extends JpaRepository<FreeReplyEntity, Long>{
	
	//board번호로 댓글 조회
	List<FreeReplyEntity> findByBoard(FreeBoardEntity board2);

	//join
	@Query("select b from #{#entityName} b join b.board ")
	List<FreeReplyEntity> findAllWithReplyUsingJoin();
	//joinfetch
	@Query("select b from #{#entityName} b join fetch b.board ")
	List<FreeReplyEntity> findAllWithReplyUsingJoinFetch();
	
}