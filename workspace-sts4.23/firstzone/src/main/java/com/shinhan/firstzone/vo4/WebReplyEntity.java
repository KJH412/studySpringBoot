package com.shinhan.firstzone.vo4;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shinhan.firstzone.vo3.FreeBoardEntity;
import com.shinhan.firstzone.vo3.FreeReplyEntity;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString (exclude = "board") //board를 원할 때만 get해서 사용하기 위해서 연관관계를 끊음
@Entity 
@Table(name = "t_webreply")
public class WebReplyEntity {
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
	private WebBoardEntity board;
}