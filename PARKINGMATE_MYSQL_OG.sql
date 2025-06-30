#테이블생성
#맨 하단의 시퀀스생성

SHOW TABLES;
SELECT * FROM USER;
SELECT * FROM ask;
SELECT * FROM PARKINGLOT;
SELECT * FROM BOOKING;

insert into ask (id, type, title, content) values('asd123', '주차장', 'cex', 'asd');

INSERT INTO user VALUES ('asd123', '1234', '김두원', 123, '12345', 'ㅇㄹㄷㅇ@BNA', 0, 0);

INSERT INTO user(id, pwd, name, resident_num, tel, email) VALUES('ROSA1', '12341', '김로사1', 1231, '123451', 'ㅇㄹㄷ1ㅇ@BNA');

INSERT INTO user(id, pwd, name, resident_num, tel, email) VALUES('admin', '1234', '관리자', 1231, '123451', 'ㅇㄹㄷ1ㅇ@BNA');

#버전과 서버 확인
SELECT VERSION();
SHOW VARIABLES LIKE 'version_comment';

DROP TABLE IF EXISTS PARKINGLOT;

#유저테이블
CREATE TABLE user (
    id	VARCHAR(50) PRIMARY KEY,
	pwd	VARCHAR(100) NOT NULL,
	name VARCHAR(100) NOT NULL,
	resident_num VARCHAR(200) NOT NULL,
	tel	VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	mate_use INT(10) DEFAULT 0 NOT NULL,
	point INT(50) DEFAULT 0 NOT NULL
);

insert into ask values('ROSA1', '문의', 'ㅇㅇ', 'ㅇㅇㅇ', '', 'ㅇㅇ');

#문의테이블
CREATE TABLE ask (
	id VARCHAR(50),
	type VARCHAR(50) NOT NULL,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(1000) NOT NULL,
	upload VARCHAR(200),
	comment VARCHAR(200),
    division int(1)
	#FOREIGN KEY 설정 (id -> user 테이블의 id 참조)
	CONSTRAINT fk_ask_id foreign key(id) references user(id)
);

drop table ask;

INSERT INTO parkinglot
  (name, addr, type, price, price2, time, latitude, longitude) VALUES
  ('주차장A','서울시 강남구','실내',3000,5000,'2025-06-18',37.5665, 126.9780);

#시퀀스용확인쿼리문
SELECT LAST_INSERT_ID() AS parkinglot_idx;

SELECT * FROM parkinglot;

#주차장테이블
CREATE TABLE parkinglot (
	idx	INT(100) AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(200) NOT NULL UNIQUE,
	addr VARCHAR(200) NOT NULL,
	type VARCHAR(100) NOT NULL,
	price INT(100) NOT NULL,
	price2 INT(100) NOT NULL,
	time DATE NOT NULL,
	valet INT(50) DEFAULT 0 NOT NULL,
    maxnum INT(50) DEFAULT 0 NOT NULL,
	obstacle INT(50) DEFAULT 0 NOT NULL,
	maxheight INT(50) DEFAULT 0 NOT NULL,
	maxwidth INT(50) DEFAULT 0 NOT NULL,
	maxweight INT(50) DEFAULT 0 NOT NULL,
    latitude double DEFAULT 0 NOT NULL,
    longitude double DEFAULT 0 NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=200;

INSERT INTO `booking` (
  `bookingnum`, `bookingdate`, `bookingcarnum`, `outime`, `idx`, `id`
) VALUES (
  'BKG0001', '2025-06-18', '12가1234', '2025-06-18 14:30:00',
200, 'ROSA1'
);

SELECT * FROM BOOKING;

#예약내역테이블
CREATE TABLE booking (
	bookingnum INT auto_increment PRIMARY KEY,
	bookingdate	DATETIME NOT NULL,
	bookingcarnum VARCHAR(300)	NOT NULL,
	intime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	outime DATETIME NOT NULL,
	valet INT(50) DEFAULT 0 NOT NULL,
	instand	INT(50) DEFAULT 0 NOT NULL,
	price INT(100) DEFAULT 0 NOT NULL,
	status VARCHAR(100) DEFAULT '예약접수' NOT NULL,
	obstacle INT(5) DEFAULT 0 NOT NULL,
	idx	INT(100),
	id VARCHAR(50),
	# FOREIGN KEY 설정 (idx-> parkinglot 테이블의 idx 참조)
	CONSTRAINT fk_booking_idx foreign key(idx) references parkinglot(idx),
	# FOREIGN KEY 설정 (id -> user 테이블의 id 참조)
	CONSTRAINT fk_booking_id foreign key(id) references user(id)
) ENGINE=InnoDB;

SELECT * FROM PARKINGMATE;

INSERT INTO `parkingmate` (
  `license`, `history`, `picture`, `addr`, `bank`, `account`, `id`
) VALUES (
  '12가3456',
  '서울시 강남구 5년 차 운행',
  '/images/pm1.jpg',
  '서울 강남구 역삼동',
  '국민은행',
  1234567890,
  'ROSA1' 
);

SELECT LAST_INSERT_ID() AS parkingmate_idx;

#파킹메이트등록테이블
CREATE TABLE parkingmate (
	idx INT(200) AUTO_INCREMENT PRIMARY KEY,
	license VARCHAR(300) NOT NULL,
	history VARCHAR(300) NOT NULL,
	picture VARCHAR(300) NOT NULL,
	addr VARCHAR(300) NOT NULL,
    bank VARCHAR(100) NOT NULL,
	account INT(100) NOT NULL,
	id VARCHAR(50),
	#FOREIGN KEY 설정 (id -> user 테이블의 id 참조)
	CONSTRAINT fk_parkingmate_id foreign key(id) references user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

INSERT INTO `community` (
  `id`, `title`, `content`
) VALUES (
  'ROSA1',
  '첫 번째 커뮤니티 글',
  '안녕하세요! 파킹메이트 커뮤니티에 오신 것을 환영합니다.'
);

SELECT LAST_INSERT_ID() AS community_idx;
select * from community;

#커뮤니티게시판테이블
CREATE TABLE community (
	idx INT(100) AUTO_INCREMENT PRIMARY KEY,
	id VARCHAR(50),
	title VARCHAR(100) NOT NULL,
	content VARCHAR(1000) NOT NULL,
	writedate DATETIME DEFAULT NOW() NOT NULL,
	readnum INT(10) DEFAULT 0 NOT NULL,
	good INT(10) DEFAULT 0 NOT NULL,
	bad INT(10) DEFAULT 0 NOT NULL,
	-- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
	CONSTRAINT fk_community_id foreign key(id) references user(id)
) ENGINE=InnoDB AUTO_INCREMENT=300;


INSERT INTO `comment` (
  `idx`, `id`, `content`
) VALUES (
  300,       -- community.idx = 1번 글
  'ROSA1',
  '첫 댓글 남깁니다. 유용한 정보 감사합니다!'
);

SELECT LAST_INSERT_ID() AS comment_idx;
select * from comment;

#커뮤니티댓글테이블
CREATE TABLE comment (
	idx2 INT(100) AUTO_INCREMENT PRIMARY KEY,
	idx INT(100),
	id VARCHAR(50),
	content VARCHAR(1000) NOT NULL,
	ref INT(50) DEFAULT 0 NOT NULL,
	lev INT(50) DEFAULT 0 NOT NULL,
	sunbun INT(50) DEFAULT 0 NOT NULL,
	-- FOREIGN KEY 설정 (id -> community 테이블의 idx 참조)--
	CONSTRAINT fk_comment_idx foreign key(idx) references community(idx),
	-- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
	CONSTRAINT fk_comment_id foreign key(id) references user(id)
)ENGINE=InnoDB AUTO_INCREMENT=400;


INSERT INTO `car_type` (
  `modelname`, `brandname`
) VALUES (
  'Model S', 'Tesla'
);

SELECT * FROM CAR_TYPE;
#차유형
CREATE TABLE car_type (
	modelname VARCHAR(300)	PRIMARY KEY,
	brandname VARCHAR(100)	NOT NULL,
	length INT(50) DEFAULT 0 NOT NULL,
	width INT(50) DEFAULT 0 NOT NULL,
	height INT(50) DEFAULT 0 NOT NULL,
	weight INT(50) DEFAULT 0 NOT NULL
);


INSERT INTO `user_cars` (
  `car_num`, `model_year`, `gas_type`, `color`, `gear`, `id`, `modelname`
) VALUES (
  '12가3456', 2023, 'gasoline', 'Red', 'automatic', 'ROSA1', 'Model S'
);

SELECT * FROM USER_CARS;

#사용자차량정보테이블
CREATE TABLE user_cars (
	car_num VARCHAR(300) PRIMARY KEY,
	model_year INT(50) NOT NULL,
	gas_type VARCHAR(50) NOT NULL,
	color VARCHAR(50) NOT NULL,
	gear VARCHAR(50) NOT NULL,
	id VARCHAR(50),
	modelname VARCHAR(300),
	-- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
	CONSTRAINT fk_user_cars_id foreign key(id) references user(id),
	-- FOREIGN KEY 설정 (modelname ->  car_type 테이블의 modelname 참조)--
	CONSTRAINT fk_user_cars_modelname foreign key(modelname) references car_type(modelname)
);


INSERT INTO mate_paycheck (
  id, endtime, mid, car_num
) VALUES (
  'ROSA1',
  '2025-07-01 10:00:00',   -- 정산 종료 시각
  'ROSA1',
  '12가3456'
);

SELECT * FROM MATE_PAYCHECK;

#파킹메이트정산테이블
CREATE TABLE mate_paycheck (
	idx INT(200) AUTO_INCREMENT PRIMARY KEY, 
	id	VARCHAR(50),
	starttime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	endtime DATETIME NOT NULL,
	status VARCHAR(200) DEFAULT '정산가능' NOT NULL,
	price INT(250) DEFAULT 0 NULL,
	mid VARCHAR(50),
	car_num VARCHAR(300),
	-- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
	CONSTRAINT fk_mate_paycheck_id foreign key(id) references user(id),
	-- FOREIGN KEY 설정 (mid -> user 테이블의 id 참조)--
	CONSTRAINT fk_mate_paycheck_mid foreign key(mid) references user(id),
	-- FOREIGN KEY 설정 (car_num ->  user_cars 테이블의 car_num 참조)--
	CONSTRAINT fk_mate_paycheck_car_num foreign key(car_num) references user_cars(car_num)	
) ENGINE=InnoDB AUTO_INCREMENT=100;


INSERT INTO `review` (
  `id`, `bookingnum`, `content`
) VALUES (
  'ROSA1',
  '1',
  '예약 경험이 매우 만족스러웠습니다. 다시 이용할게요!'
);

SELECT * FROM REVIEW;

CREATE TABLE review (
	id VARCHAR(50),
	bookingnum INT,
	writedate DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	rating INT(2) DEFAULT 0 NOT NULL,
	content VARCHAR(1000) NOT NULL,
	upload VARCHAR(500),
	-- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
	CONSTRAINT fk_review_id foreign key(id) references user(id),
	-- FOREIGN KEY 설정 (bookingnum -> booking 테이블의 bookingnum 참조)--
	CONSTRAINT fk_review_bookingnum foreign key(bookingnum) references booking(bookingnum) 
);

create table notice (
   idx int AUTO_INCREMENT primary key,
    id varchar(50) not null,
    title varchar(100) NOT NULL,
    content varchar(1000) NOT NULL,
    writedate DATETIME NOT NULL,
    readnum int NOT NULL,
    division int NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1;

create table notice_poto (
   idx int AUTO_INCREMENT PRIMARY KEY,
    notice_num int,
    poto_1 varchar(100),
    poto_2 varchar(100),
    poto_3 varchar(100),
    poto_4 varchar(100),
    
    CONSTRAINT fk_notice_num foreign key(notice_num) references notice(idx)
)ENGINE=InnoDB AUTO_INCREMENT=1;

create table faq (
	 idx int AUTO_INCREMENT PRIMARY KEY,
     question varchar(100) NOT NULL,
     answer varchar(1000) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1;

commit;

select * from notice;

select * from faq;

delete from notice where idx = 7;

update notice set title = '1', content = '1' where idx = 1;

select count(*) from notice where division = 0;

select max(ref) from notice where division = 0;


		SELECT * FROM notice
    	WHERE division = 0
    	ORDER BY idx DESC
    	LIMIT 0, 5;
drop table faq;

insert into notice (id, title, content, writedate, readnum, division) values ('관리자','8','8',now(),0,1)