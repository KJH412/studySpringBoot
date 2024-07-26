package com.shinhan.firstzone.repository4;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.WebBoardEntity;

//interface설계
//1.JpaRepository , QuerydslPredicateExecutor 상속 : 기본제공함수들을 사용 가능
//2.규칙이 있는 함수 정의: findy ~~
//3.JPQL : select -> @Query사용  , DML -> @Modifying @Query사용
//4.Querydsl이용하면 동적 SQL생성 가능
public interface WebBoardRepository extends JpaRepository<WebBoardEntity, Long>,
						QuerydslPredicateExecutor<WebBoardEntity>{
	
	
	Page<WebBoardEntity> findByWriter(MemberEntity member, Pageable paging);
	//Page<WebBoardEntity> -> List<WebBoardDTO>  
	//: WebBoardEntity에서 WebBoardDTO로, <>타입은 사용하는 쪽에서 결정함
	
 
}
