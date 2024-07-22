package com.shinhan.firstzone.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //빌더패턴 사용가능한 코드생성(체이닝코드)
@Data //getter+setter+toString
@NoArgsConstructor
@AllArgsConstructor
public class CarVO {
	String model;
	int price;
}