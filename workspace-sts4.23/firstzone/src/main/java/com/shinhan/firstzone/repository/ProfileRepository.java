package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo2.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

	//1.기본 CRUD 제공
	
	//2.규칙에 맞는 함수정의
	
	//특정 Member의 profile가져오기
	//from t_profile where member_mid = 'user5' and currentYn = 1 
	List<ProfileEntity> findByMemberAndCurrentYnTrue(MemberEntity member);
	List<ProfileEntity> findByCurrentYnTrue();
	
	//3.@Query이용 -- 특정 Member의 profile가져오기
	//outer 조인을 할 필요 없음=> 특정 mid에 해당하는 profile이 무조건 존재하기 때문에
	//select p 만 찾을 경우, List<Objcet[]>타입이 아니라 List<ProfileEntity>로 받아야함.
	@Query(" select p.fname, m.mname from ProfileEntity p "
			+ " join MemberEntity m on(p.member = m)"
			+ " where m.mid = ?1")
	List<Object[]> getProfile(String mid); //mid는 MemberEntity에 있음.
	

	//모든 멤버의 mid, profile의 count
	//*왼쪽 엔티티(Member)가 기준이고, 오른쪽 엔티티는 없을 수 도 있기 때문에 left outer join. (다(m)대일(p)관계)  
	@Query(" select m.mid, count(p) from MemberEntity m "
			+ " left outer join ProfileEntity p on(m = p.member)"
			+ " group by m.mid")
	List<Object[]> getProfileCount();










}
