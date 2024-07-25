package com.shinhan.firstzone.vo4;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shinhan.firstzone.vo2.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

 
//DTO(Data Transfer Object) 데이터 전송 객체
//VO : Value Object <= (값을 넣어 전송할 객체) Map같은? Map을 사용하는 것 보다 관리 쉬움.

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebBoardDTO {  
	private Long bno;
	private String title;
//	private MemberEntity writer; 
	private String mid;
	private String mname;
	private String content;
	private Timestamp regdate;
	private Timestamp updatedate;
	private int replyCount;
}
