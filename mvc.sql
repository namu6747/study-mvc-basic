CREATE TABLE mvc_member(
	num INT primary key auto_increment,
	id VARCHAR(50) UNIQUE NOT NULL,
	pass VARCHAR(50) NOT NULL,
	name VARCHAR(50),
	age INT(3) default 0,
	gender VARCHAR(10),
	joinYN char(1) DEFAULT 'Y',
	regdate TIMESTAMP default now(),
	updatedate TIMESTAMP default now()
);
commit;

SELECT * FROM mvc_member;

-- 비밀번호 찾기에 사용될 코드를 저장할 테이블
CREATE TABLE test_code(
	id VARCHAR(50),
	code char(5)
);



-- 관리자 계정 추가
INSERT INTO mvc_member(id,pass,name,age,gender) 
VALUES('admin','admin','MASTER',0,'male');











-- 공 지 사 항
DESC notice_board;

SELECT * FROM notice_board
ORDER BY n_num DESC limit 0, 10;


