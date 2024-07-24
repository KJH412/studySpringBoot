package com.shinhan.firstzone.vo3;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MultiKeyA implements Serializable{
	//Serializable: 줄 세워서 늘어뜨림 나중에 다시 복구하려면 Serializable 번호가 필요?
	//직렬화: 객체를 바이트 스트림으로 변환하는 과정
	//serialVershionUID: 직렬화된 객체의 버전을 식별하기 위한 고유한 ID
	//serialVersionUID를 명시적으로 지정하는 것이 좋은 이유는?
	//=>클래스의 변경 사항에도 불구하고 직렬화된 객체를 안정적으로 역직렬화할 수 있기 때문
	private static final long serialVershionUID = 1L;
	Integer id1;
	Integer id2;
}