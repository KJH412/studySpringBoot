package com.shinhan.firstzone.vo2;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@MappedSuperclass //<= 실제 table 생성 안됨.
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity {//모든 클래스에서 사용 가능한 추상 클래스
	
	@CreatedDate  //hibernate 어노테이션
	@Column(name = "regdate", updatable = false) //updatable=false :수정 불가
	LocalDateTime regDate;
	
	@LastModifiedDate   //--> @EntityListeners 꼭 필요
	@Column(name = "moddate")
	LocalDateTime modDate;
	
	
}