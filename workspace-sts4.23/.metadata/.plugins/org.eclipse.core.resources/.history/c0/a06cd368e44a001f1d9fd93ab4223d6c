package com.shinhan.firstzone.vo2;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_member")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberEntity {
	
	@Id //Primary Key
	String mid;
	String mpassword;
	String mname;
	@Enumerated(EnumType.STRING) //기본은 순서 ordinal이 입력됨. Enum 문자타입
	MemberRole mrole; 
	//USER, MANAGER, ADMIN 권한이 3개 뿐이라 Enum으로 만듦(MemberRole.java)
}
