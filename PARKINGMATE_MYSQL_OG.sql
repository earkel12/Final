#테이블생성
#순서대로 생성하기
#유저테이블
CREATE TABLE user (
    id   VARCHAR(50) PRIMARY KEY,
   pwd   VARCHAR(100) NOT NULL,
   name VARCHAR(100) NOT NULL,
   resident_num VARCHAR(200) NOT NULL,
   tel   VARCHAR(100) NOT NULL,
   email VARCHAR(100) NOT NULL,
   mate_use INT DEFAULT 0 NOT NULL,
   point INT DEFAULT 0 NOT NULL
);

#문의테이블
CREATE TABLE ask (
   idx INT AUTO_INCREMENT PRIMARY KEY,
   id VARCHAR(50),
   type VARCHAR(50) NOT NULL,
   title VARCHAR(50) NOT NULL,
   content VARCHAR(1000) NOT NULL,
   upload VARCHAR(200),
   comment VARCHAR(200),
    division INT,
   #FOREIGN KEY 설정 (id -> user 테이블의 id 참조)
   CONSTRAINT fk_ask_id foreign key(id) references user(id)
)ENGINE=InnoDB AUTO_INCREMENT=1;

#주차장테이블
CREATE TABLE parkinglot (
   idx   INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(200) NOT NULL UNIQUE,
   addr VARCHAR(200) NOT NULL,
   type VARCHAR(100) NOT NULL,
   price INT NOT NULL,
   price2 INT NOT NULL,
   valet INT DEFAULT 0 NOT NULL,
    maxnum INT DEFAULT 0 NOT NULL,
   obstacle INT DEFAULT 0 NOT NULL,
   maxheight INT DEFAULT 0 NOT NULL,
   maxwidth INT DEFAULT 0 NOT NULL,
   maxweight INT DEFAULT 0 NOT NULL,
   latitude double DEFAULT 0 NOT NULL,
    longitude double DEFAULT 0 NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=200;

#예약내역테이블
CREATE TABLE booking (
   bookingnum INT AUTO_INCREMENT PRIMARY KEY,
   bookingdate   DATETIME NOT NULL,
   bookingcarnum VARCHAR(300) NOT NULL,
   intime DATETIME,
   outtime DATETIME,
   valet INT DEFAULT 0 NOT NULL,
   instand   INT DEFAULT 0 NOT NULL,
   price INT DEFAULT 0 NOT NULL,
    -- 상태는 주차장이용과 주차+메이트이용 경우에 따라 아래와 같이 구분됨
    -- 주차장이용: 예약접수 -> 주차장결제완료
    -- 주차+메이트이용: 예약접수 -> 파킹메이트결제완료 -> 파킹메이트매칭완료 -> 주차장결제완료.
   status VARCHAR(100) DEFAULT '예약접수' NOT NULL,
   obstacle INT DEFAULT 0 NOT NULL,
   idx   INT,
   id VARCHAR(50),
   ulatitude DOUBLE DEFAULT 0,
    ulongitude DOUBLE DEFAULT 0,
    pmlatitude DOUBLE DEFAULT 0,
    pmlongitude DOUBLE DEFAULT 0,
   # FOREIGN KEY 설정 (idx-> parkinglot 테이블의 idx 참조)
   CONSTRAINT fk_booking_idx foreign key(idx) references parkinglot(idx),
   # FOREIGN KEY 설정 (id -> user 테이블의 id 참조)
   CONSTRAINT fk_booking_id foreign key(id) references user(id)
) ENGINE=InnoDB;

#파킹메이트등록테이블
CREATE TABLE parkingmate (
   idx INT AUTO_INCREMENT PRIMARY KEY,
   license VARCHAR(300) NOT NULL,
   history VARCHAR(300) NOT NULL,
   picture VARCHAR(300) NOT NULL,
   addr VARCHAR(300) NOT NULL,
    bank VARCHAR(100) NOT NULL,
   account bigint NOT NULL,
   id VARCHAR(50),
   #FOREIGN KEY 설정 (id -> user 테이블의 id 참조)
   CONSTRAINT fk_parkingmate_id foreign key(id) references user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

#커뮤니티게시판테이블
CREATE TABLE community (
   idx INT AUTO_INCREMENT PRIMARY KEY,
   id VARCHAR(50),
   title VARCHAR(100) NOT NULL,
   content VARCHAR(1000) NOT NULL,
   writedate DATETIME DEFAULT NOW() NOT NULL,
   readnum INT DEFAULT 0 NOT NULL,
   good INT DEFAULT 0 NOT NULL,
   bad INT DEFAULT 0 NOT NULL,
   -- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
   CONSTRAINT fk_community_id foreign key(id) references user(id)
) ENGINE=InnoDB AUTO_INCREMENT=300;

#커뮤니티댓글테이블
CREATE TABLE comment (
   idx2 INT AUTO_INCREMENT PRIMARY KEY,
   idx INT,
   id VARCHAR(50),
   content VARCHAR(1000) NOT NULL,
   ref INT DEFAULT 0 NOT NULL,
   lev INT DEFAULT 0 NOT NULL,
   sunbun INT DEFAULT 0 NOT NULL,
   -- FOREIGN KEY 설정 (id -> community 테이블의 idx 참조)--
   CONSTRAINT fk_comment_idx foreign key(idx) references community(idx),
   -- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
   CONSTRAINT fk_comment_id foreign key(id) references user(id)
)ENGINE=InnoDB AUTO_INCREMENT=400;

#차유형
CREATE TABLE car_type (
   modelname VARCHAR(300)   PRIMARY KEY,
   brandname VARCHAR(100)   NOT NULL,
   length INT DEFAULT 0 NOT NULL,
   width INT DEFAULT 0 NOT NULL,
   height INT DEFAULT 0 NOT NULL,
   weight INT DEFAULT 0 NOT NULL
);

#사용자차량정보테이블
CREATE TABLE user_cars (
   car_num VARCHAR(300) PRIMARY KEY,
   model_year INT NOT NULL,
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

#파킹메이트정산테이블
CREATE TABLE mate_paycheck (
   idx INT AUTO_INCREMENT PRIMARY KEY, 
   id   VARCHAR(50),
   starttime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
   endtime DATETIME NOT NULL,
    -- 상태틑 2가지로 구분됨 1.정산대기 2.정산완료
   status VARCHAR(200) DEFAULT '정산대기' NOT NULL,
   price INT DEFAULT 0 NULL,
   mid VARCHAR(50),
   car_num VARCHAR(300),
   -- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
   CONSTRAINT fk_mate_paycheck_id foreign key(id) references user(id),
   -- FOREIGN KEY 설정 (mid -> user 테이블의 id 참조)--
   CONSTRAINT fk_mate_paycheck_mid foreign key(mid) references user(id),
   -- FOREIGN KEY 설정 (car_num ->  user_cars 테이블의 car_num 참조)--
   CONSTRAINT fk_mate_paycheck_car_num foreign key(car_num) references user_cars(car_num)   
) ENGINE=InnoDB AUTO_INCREMENT=100;

#리뷰테이블
CREATE TABLE review (
   id VARCHAR(50),
   bookingnum INT,
   writedate DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
   rating INT DEFAULT 0 NOT NULL,
   content VARCHAR(1000) NOT NULL,
   upload VARCHAR(500),
   -- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
   CONSTRAINT fk_review_id foreign key(id) references user(id),
   -- FOREIGN KEY 설정 (bookingnum -> booking 테이블의 bookingnum 참조)--
   CONSTRAINT fk_review_bookingnum foreign key(bookingnum) references booking(bookingnum) 
);

#공지사항테이블
create table notice (
   idx INT AUTO_INCREMENT primary key,
    id VARCHAR(50),
    title VARCHAR(100) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    writedate DATETIME NOT NULL,
    readnum INT NOT NULL,
    division INT NOT NULL,
    -- FOREIGN KEY 설정 (id -> user 테이블의 id 참조)--
   CONSTRAINT fk_notice_id foreign key(id) references user(id)
)ENGINE=InnoDB AUTO_INCREMENT=1;

#공지사항관련 사진업로드테이블
create table notice_poto (
   idx INT AUTO_INCREMENT PRIMARY KEY,
    notice_num INT,
    poto_1 VARCHAR(100),
    poto_2 VARCHAR(100),
    poto_3 VARCHAR(100),
    poto_4 VARCHAR(100),
    -- FOREIGN KEY 설정 (notice_num -> notice 테이블의 idx 참조)--
    CONSTRAINT fk_notice_num foreign key(notice_num) references notice(idx)
)ENGINE=InnoDB AUTO_INCREMENT=1;

#자주하는질문(FAQ)테이블
create table faq (
    idx INT AUTO_INCREMENT PRIMARY KEY,
     question VARCHAR(100) NOT NULL,
     answer VARCHAR(1000) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1;
