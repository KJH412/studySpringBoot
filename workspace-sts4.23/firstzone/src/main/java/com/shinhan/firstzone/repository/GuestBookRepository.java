package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.firstzone.vo2.GuestBookEntity;

/* interface 설계 */
//CrudRepository : 기본 CRUD 생성
//PagingAndSortingRepository  : 페이징
//QuerydslPredicateExecutor : 동적 sql 문장 만듦
public interface GuestBookRepository extends CrudRepository<GuestBookEntity, Long>,
				 PagingAndSortingRepository<GuestBookEntity, Long>,
				 QuerydslPredicateExecutor<GuestBookEntity>{
	
	//1. 기본 CRUD함수 제공, PagingAndSorting 가능
	
	//2. 규칙에 맞는 함수 추가 기능
	List<GuestBookEntity> findByRegDateIsNull(); //RegDate이름은 데이터기준이 아니라 자바 필드 기준
	//3. @Query : sql쿼리를 직접 작성해도 된다.
	@Query("select b from #{#entityName} b where b.regDate is null") //자바 기준 필드명
	List<GuestBookEntity> findByRegDateIsNull2();
	
	@Query("select b from #{#entityName} b where b.regDate is null and b.gno> ?1")
	List<GuestBookEntity> findByRegDateIsNull4(Long gno);
	
	
	//4. @Query, nativeQuery 가능 (nativeQuery 남발 금지)
	@Query(value = "select * from t_guestbook b where b.regDate is null", nativeQuery = true)
	List<GuestBookEntity> findByRegDateIsNull3();
}
