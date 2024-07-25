package com.shinhan.firstzone.vo4;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shinhan.firstzone.vo2.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString(exclude = "replies") //replies은 원할 때만 get해서 사용하기 위해서 연관관계를 끊음
@Entity 
@Table(name = "t_webboard")
public class WebBoardEntity {
	
	@Id//PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	private String title;
	
	//1명이 게시글을 여러개 쓸 수 있기 때문에 @ManyToOne
	@ManyToOne
	private MemberEntity writer;  //writer_mid
	
	private String content;
	@CreationTimestamp //insert시 자동 생성
	private Timestamp regdate;
	@UpdateTimestamp //insert , update시 자동 생성
	private Timestamp updatedate;
	
	@OneToMany(mappedBy = "board"   //mappedBy: PK자신이 다른 객체에 매여있음
			,cascade = CascadeType.ALL //cascade: WebReplyEntity에 '전이'된다.
			,fetch = FetchType.EAGER)   //LAZY-지연로딩 ,EAGER-즉시로딩
	List<WebReplyEntity> replies;
	
}
