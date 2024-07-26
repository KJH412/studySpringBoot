package com.shinhan.firstzone.repository4;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.firstzone.vo4.WebBoardEntity;
import com.shinhan.firstzone.vo4.WebReplyEntity;

public interface WepReplyRepository extends JpaRepository<WebReplyEntity, Long>,
					QuerydslPredicateExecutor<WebReplyEntity>{
	
	//특정 보드에 해당하는 댓글들
	List<WebReplyEntity> findByBoard(WebBoardEntity board);
}	