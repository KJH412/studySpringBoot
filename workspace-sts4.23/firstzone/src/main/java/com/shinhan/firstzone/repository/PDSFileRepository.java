package com.shinhan.firstzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.firstzone.vo2.PDSFileEntity;

//File
public interface PDSFileRepository extends JpaRepository<PDSFileEntity, Long>{
	//1.기본 CRUD함수 제공된다.
	
	//2.규칙에 맞는 함수 정의
	/*public abstract*/PDSFileEntity findByPdsfilename(String pname);
	
}
