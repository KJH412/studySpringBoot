package com.shinhan.firstzone.vo;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity ///JPA가 관리하는 대상 ...DB의 table과 Mapping할 때 사용
@Table(name = "t_board") //자바클래스이름(BoardEntity)으로 테이블 명이 생성되기 때문에 작성
public class BoardEntity {
	
	@Id //PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//자바의 기본 접근자 private
	private Long bno;
	
	@Column(length = 50)
	private String title;
	
	private String content;
	private String writer;

	//Timestamp - 자동으로 값 들어감
	//보통 대문자 기준 언더바(reg_date)가 생성되기 때문에 필드와 같은 이름을 사용하고 싶을 때 사용
	@Column(name = "regdate") 
	@CreationTimestamp //insert시 입력
	private Timestamp regDate; 
	
	@Column(name = "updatedate") 
	@UpdateTimestamp //insert시 입력, 수정시 변경
	private Timestamp updateDate; //Timestamp : sql타입 주의
}
