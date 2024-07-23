package com.shinhan.firstzone.vo2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "t_guestbook")
@Entity //JPA가 관리하는 객체(DB의 객체와 자바의 객체를 Mapping)
@Builder  //@Builder는 꼭 @AllArgsConstructor가 필요하고,
@AllArgsConstructor //@AllArgsConstructor를 넣으면 @NoArgsConstructor가 없어져서 꼭 적어줘야한다.
@NoArgsConstructor
//@Data //getter,setter,toString,equalsandhashcode,Noargumentconstructor
@Getter@Setter
public class GuestBookEntity extends BaseEntity { 
	
	//다 완성한 후 서버 실행하면 테이블 생성됨.
	 
	@Id //PK (중요-레파지토리를 만들 때 key를 요구한다.)
	@GeneratedValue(strategy = GenerationType.IDENTITY) //GenerationType.AUTO : DB에 맞춰 자동으로 생성 , 시퀀스가 자동으로 생성된다.
	Long gno;
	@Column(length = 100, nullable = false)
	String title;
	@Column(length = 2000)
	String content;
	@Column(length = 50)
	String writer;
	
	//상속받은 부모의 필드도 같이 보고싶음.
	@Override
	public String toString() {
		return "GuestBookEntity [gno=" + gno + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", regDate(등록일)=" + regDate + ", modDate(수정일)=" + modDate + "]";
	}
	
	
	/* 기업에서 많이 사용하는 등록일과 수정일은 BaseEntity로 따로 분리해서 재사용한다
	@CreationTimestamp  //hibernate 어노테이션
	@Column(name = "regdate", updatable = false) //updatable=false :수정 불가
	LocalDateTime regDate;
	@LastModifiedDate
	@Column(name = "moddate")
	LocalDateTime modDate;
	*/
}
