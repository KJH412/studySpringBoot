package com.shinhan.firstzone.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;  //타입 주의
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.firstzone.vo.BoardEntity;

//Repository설계
//JPA -> interface기반으로 구현class를 만듦
//CrudRepository: 기본 CRUD
//PagingAndSortingRepository: 
public interface BoradRepository extends CrudRepository<BoardEntity, Long>, //<테이블명,pk타입> //기본 CRUD
										 PagingAndSortingRepository<BoardEntity, Long>{ //기본 CRUD + paging,sort
	
	//1. 기본 CRUD는 제공: findAll(), findById(),save(), count(), deleteById();
	//2. 규칙에 맞는 함수를 정의
	//https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods
	List<BoardEntity> findByWriter(String writer);
	
	//like 문 3가지 방법
	List<BoardEntity> findByTitleStartingWith(String title); //where title 'A%'
	List<BoardEntity> findByTitleEndingWith(String title); //where title '%A'
	List<BoardEntity> findByTitleContaining(String title); //where title '%A%'
	
	//where bno >= ? and bno <= ?
	List<BoardEntity> findByBnoBetween(Long bno1, Long bno2);
	//이런 문장도 됨 Between~And
	//List<BoardEntity> findByIdGreaterThanEqualAndIdLessThanEqual(Long bno1, Long bno2);

	//where content like '%aa%' and (bno >= ? and bno <= ?) order by bno desc
	List<BoardEntity> findByContentLikeAndBnoBetweenOrderByBnoDesc(String content, Long bno1, Long bno2);
	//findByContentLike  And  BnoBetween  OrderByBno Desc
	
	//**paging추가
	//where bno > ? order by bno desc
	List<BoardEntity> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable pageable);
	
	//3.JPQL(JPA Query Language)이용하기 : 복잡한 SQL문 생성시 이용한다.
	//주의 : nativeQuery와 비슷하지만 같지는 않다. 예를들면, select * from... (*불가)
	//from 다음에 엔티티 이름이 오고 별칭을 준다.
	@Query(" select b from BoardEntity b "
			+ " where b.title like %?1% and b.content like %?2% ") // ''을 주면 고정값 문자열이 됨.
	List<BoardEntity> jpdlTest1(String title, String content); //%?1%=title, %?2%=content
	
	@Query(" select b from BoardEntity b "
			+ " where b.title like %:tt% and b.content like %:cc% order by bno desc") //@Param으로 준 이름을 변수에 넣을 수 있다.
	List<BoardEntity> jpdlTest2(@Param("tt") String title, @Param("cc") String content); 
	
	@Query(" select b from #{#entityName} b "  //엔티티이름을 찾아 넣는다.
			+ " where b.title like %:tt% and b.content like %:cc% order by bno desc") //@Param으로 준 이름을 변수에 넣을 수 있다.
	List<BoardEntity> jpdlTest3(@Param("tt") String title, @Param("cc") String content); 
	
	@Query(" select b.title, b.content, b.writer from #{#entityName} b "  
			+ " where b.title like %:tt% and b.content like %:cc% order by bno desc") //@Param으로 준 이름을 변수에 넣을 수 있다.
	List<Object[]> jpdlTest4(@Param("tt") String title, @Param("cc") String content); // b.title, b.content, b.writer 어떤 타입이 올 지 모르기 때문에 Object[]으로 받는다.
	
	//nativeQuery (JPQL이 아님)
	//기존 sql문을 사용하고 싶을 때 사용법
	@Query(value = " select * from t_board b "
			+ " where b.title like ?1 and b.content like ?2 order by bno desc",  
			nativeQuery = true)
	List<BoardEntity> jpdlTest5(String title, String content);
	//---> '%'||?2||'%'-오라클 문법 , 어떤 DBMS가 올 지 모르니까 자바에서 직접 처리함. 예) "%title%"
	
}
