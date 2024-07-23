package com.shinhan.firstzone.vo2;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestBookDTO { //전송용. (DTO롤 별도로 만듦) 바뀌는 기능은 서비스에서 만듦. 
	Long gno;
	String title;
	String content;
	String writer;
	LocalDateTime regDate, modDate;
	
	//DTO와 Entity가 서로 소통하는 함수를 interface(GuestBookSerivce)에 만듦.
}
