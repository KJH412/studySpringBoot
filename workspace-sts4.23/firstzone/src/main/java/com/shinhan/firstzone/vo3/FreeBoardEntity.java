package com.shinhan.firstzone.vo3;

import java.sql.Timestamp;  //security 아님 (주의)
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//▼ 엔티티로 사용 
@Table(name = "t_freeboard")
@Entity 
@Builder
@AllArgsConstructor
@NoArgsConstructor
//▼ 여기까지 쓰면 DTO 사용 목적
@Getter@Setter
@EqualsAndHashCode(of = {"bno"})//bno가 같으면 같은 객체로 본다. //객체가 여러개 있을때 모든게 같다면 같은 객체로 판단. 
@ToString(exclude = {"replies"})
public class FreeBoardEntity {
	
	//Emtity를 만들 때 private로 감싼다.
	@Id//PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY:시퀀스 자동생성X
	private Long bno;
	private String title;
	private String writer;
	private String content;
	
	@CreationTimestamp //insert시 자동 생성
	private Timestamp regdate;
	@UpdateTimestamp //insert , update시 자동 생성
	private Timestamp updatedate;
	
	//N+1해결방법중에 하나이다. Join사용시 대상 entity에 갯수만큼 상대entity를 select문이
	//in을 사용하여 BatchSize만큼 한꺼번에 select가능
	@BatchSize(size = 100)
	//하나의 board에 댓글이 여러개 있음. (부모)
	@OneToMany(mappedBy = "board"   //mappedBy: PK자신이 다른 객체에 매여있음
				,cascade = CascadeType.ALL //모든전의
				,fetch = FetchType.LAZY)   //LAZY-지연로딩 ,EAGER-즉시로딩 
	List<FreeReplyEntity> replies;

}