package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno;  // int보다 더 큰 수 할당 가능
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
}
