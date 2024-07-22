package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data //getter+setter+toString
@NoArgsConstructor
@AllArgsConstructor
public class CarVO {
	String Model;
	int price;
}