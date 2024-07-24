package com.shinhan.firstzone.vo3;

import java.sql.Timestamp;  //security 아님 (주의)

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Builder
@Entity 
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"rno"})
@ToString(exclude = {"board"})
@Getter@Setter
@Table(name = "t_freereply")
public class FreeReplyEntity {
	
	//Emtity를 만들 때 private로 감싼다.
	@Id//PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY:시퀀스 자동생성X
	private Long rno;
	private String reply;
	private String replyer;
	
	@CreationTimestamp //insert시 자동 생성
	private Timestamp regdate;
	@UpdateTimestamp //insert , update시 자동 생성
	private Timestamp updatedate;
	
	//하나의 board에 댓글이 여러개 있음. (자식)
	@ManyToOne (fetch = FetchType.EAGER) //테이블칼럼=>board_bno
	private FreeBoardEntity board;
	
}
