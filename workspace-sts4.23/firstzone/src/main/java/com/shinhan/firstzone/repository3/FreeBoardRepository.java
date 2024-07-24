package com.shinhan.firstzone.repository3;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; //domain
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


import com.shinhan.firstzone.vo3.FreeBoardEntity;

//board
//join : N+1문제 
//joinfetch : N+1문제해결
public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Long>,
					PagingAndSortingRepository<FreeBoardEntity, Long>,
					QuerydslPredicateExecutor<FreeBoardEntity>{
	
	//join
	@Query("select b from #{#entityName} b join b.replies ")
	List<FreeBoardEntity> findAllWithReplyUsingJoin();
	//joinfetch
	@Query("select b from #{#entityName} b join fetch b.replies ")
	List<FreeBoardEntity> findAllWithReplyUsingJoinFetch();
	
	//조건 조회 bno>=10 and bno<=20, paging추가
	//List<FreeBoardEntity> findByBnoBetween(Long bno1, Long bno2, Pageable pageable);
	Page<FreeBoardEntity> findByBnoBetween(Long bno1, Long bno2, Pageable pageable);
	
	//여러건 -> List ,칼럼이 두개 -> Object[]
	//left outer join : 왼쪽 테이블이 기준이 된다.
	@Query("select b.title, count(reply) "  //from FreeBoardEntity
			+ " from #{#entityName} b left outer join b.replies reply  "
			+ " group by b.title order by b.title ")
	public List<Object[]> getBoardReplyCount2();

}