--���̺����--
--�� �ϴ��� ����������-- 

--�������̺�-- 
CREATE TABLE user (
    id	VARCHAR2(50) PRIMARY KEY,
	pwd	VARCHAR2(100) NOT NULL,
	name VARCHAR2(100) NOT NULL,
	resident_num NUMBER(200)NULL,
	tel	VARCHAR2(100)	NULL,
	email VARCHAR2(100)	NULL,
	mate_use NUMBER(10)	NULL,
	point NUMBER(50) NOT NULL
);

--�������̺�-- 
CREATE TABLE ask (
	id VARCHAR2(50),
	type VARCHAR2(50) NOT NULL,
	title VARCHAR2(50) NOT NULL,
	content VARCHAR2(1000) NOT NULL,
	upload VARCHAR2(200),
	comment VARCHAR2(200) NOT NULL,
	-- FOREIGN KEY ���� (id -> user ���̺��� id ����)--
	CONSTRAINT fk_ask_id foreign key(id) references user(id)
);

--���������̺�-- 
CREATE TABLE parkinglot (
	idx	NUMBER(100)	PRIMARY KEY,
	name VARCHAR2(200) NOT NULL UNIQUE,
	addr VARCHAR2(200) NOT NULL,
	type VARCHAR2(100) NOT NULL,
	price NUMBER(100) NOT NULL,
	price2 NUMBER(100) NOT NULL,
	time DATE NOT NULL,
	valet NUMBER(50) DEFAULT 0 NOT NULL,
    maxnum NUMBER(50) DEFAULT 0 NOT NULL,
	obstacle NUMBER(50)	DEFAULT 0 NOT NULL,
	maxheight NUMBER(50) DEFAULT 0 NOT NULL,
	maxwidth NUMBER(50)	DEFAULT 0 NOT NULL,
	maxweight NUMBER(50) DEFAULT 0 NOT NULL	
);

--���೻�����̺�-- 
CREATE TABLE booking (
	bookingnum VARCHAR2(300) PRIMARY KEY,
	bookingdate	DATE NOT NULL,
	bookingcarnum VARCHAR2(300)	NOT NULL,
	intime DATE NOT NULL,
	outime DATE NOT NULL,
	valet NUMBER(50) DEFAULT 0 NOT NULL,
	instand	NUMBER(50) DEFAULT 0 NOT NULL,
	price NUMBER(100) DEFAULT 0 NOT NULL,
	status VARCHAR2(100) DEFAULT '��������' NOT NULL,
	obstacle NUMBER(5) DEFAULT 0 NOT NULL,
	idx	NUMBER(100),
	id VARCHAR2(50),
	-- FOREIGN KEY ���� (idx-> parkinglot ���̺��� idx ����)--
	CONSTRAINT fk_booking_idx foreign key(idx) references parkinglot(idx),
	-- FOREIGN KEY ���� (id -> user ���̺��� id ����)--
	CONSTRAINT fk_booking_id foreign key(id) references user(id)
);

--��ŷ����Ʈ������̺�-- 
CREATE TABLE parkingmate (
	idx NUMBER(200)	PRIMARY KEY,
	license VARCHAR2(300) NOT NULL,
	history VARCHAR2(300) NOT NULL,
	picture VARCHAR2(300) NOT NULL,
	addr VARCHAR2(300) NOT NULL,
	account NUMBER(100) NOT NULL,
	bank VARCHAR2(100) NOT NULL,
	id VARCHAR2(50),
	-- FOREIGN KEY ���� (id -> user ���̺��� id ����)--
	CONSTRAINT fk_parkingmate_id foreign key(id) references user(id)
);

--Ŀ�´�Ƽ�Խ������̺�-- 
CREATE TABLE community (
	idx NUMBER(100)	PRIMARY KEY,
	id VARCHAR2(50),
	title VARCHAR2(100)	NULL,
	content VARCHAR2(1000)	NULL,
	writedate DATE DEFAULT SYSDATE NOT NULL,
	readnum NUMBER(10) DEFAULT 0 NOT NULL,
	good NUMBER(10) DEFAULT 0 NOT NULL,
	bad NUMBER(10) DEFAULT 0 NOT NULL,
	-- FOREIGN KEY ���� (id -> user ���̺��� id ����)--
	CONSTRAINT fk_community_id foreign key(id) references user(id)
);

--Ŀ�´�Ƽ������̺�-- 
CREATE TABLE comment (
	idx NUMBER(100)	PRIMARY KEY,
	idx NUMBER(100),
	id2 VARCHAR2(50),
	content VARCHAR2(1000) NOT NULL,
	ref NUMBER(50) DEFAULT 0 NOT NULL,
	lev NUMBER(50) DEFAULT 0 NOT NULL,
	sunbun NUMBER(50) DEFAULT 0 NOT NULL,
	-- FOREIGN KEY ���� (id -> community ���̺��� idx ����)--
	CONSTRAINT fk_comment_idx foreign key(idx) references community(idx),
	-- FOREIGN KEY ���� (id -> user ���̺��� id ����)--
	CONSTRAINT fk_comment_id foreign key(id) references user(id)
);

--������-- 
CREATE TABLE car_type (
	modelname VARCHAR2(300)	PRIMARY KEY,
	brandname VARCHAR2(100)	NOT NULL,
	length NUMBER(50) DEFAULT 0 NOT NULL,
	width NUMBER(50) DEFAULT 0 NOT NULL,
	height NUMBER(50) DEFAULT 0 NOT NULL,
	weight NUMBER(50) DEFAULT 0 NOT NULL
);

--����������������̺�-- 
CREATE TABLE user_cars (
	car-num VARCHAR2(300) PRIMARY KEY,
	model_year NUMBER(50) NOT NULL,
	gas_type VARCHAR2(50) NOT NULL,
	color VARCHAR2(50) NOT NULL,
	gear VARCHAR2(50) NOT NULL,
	id VARCHAR2(50),
	modelname VARCHAR2(300),
	-- FOREIGN KEY ���� (id -> user ���̺��� id ����)--
	CONSTRAINT fk_user_cars_id foreign key(id) references user(id),
	-- FOREIGN KEY ���� (modelname ->  car_type ���̺��� modelname ����)--
	CONSTRAINT fk_user_cars_modelname foreign key(modelname) references car_type(modelname)
);

--��ŷ����Ʈ�������̺�-- 
CREATE TABLE mate_paycheck (
	idx NUMBER(200)	PRIMARY KEY, 
	id	VARCHAR2(50),
	starttime DATE NOT NULL,
	endtime DATE NOT NULL,
	status VARCHAR2(200) DEFAULT ?? NOT NULL,
	price NUMBER(300) DEFAULT 0 NULL,
	mid VARCHAR2(50),
	car-num VARCHAR2(300),
	-- FOREIGN KEY ���� (id -> user ���̺��� id ����)--
	CONSTRAINT fk_mate_paycheck_id foreign key(id) references user(id),
	-- FOREIGN KEY ���� (mid -> user ���̺��� id ����)--
	CONSTRAINT fk_mate_paycheck_mid foreign key(mid) references user(id),
	-- FOREIGN KEY ���� (car_num ->  user_cars ���̺��� car_num ����)--
	CONSTRAINT fk_mate_paycheck_car_num foreign key(car_num) references user_cars(car_num)	
);

CREATE TABLE review (
	id VARCHAR(50),
	bookingnum`	VARCHAR(300),
	writedate DATE NOT NULL,
	rating`	NUMBER(2) DEFAULT 0 NOT NULL,
	content VARCHAR2(1000) NOT NULL,
	upload VARCHAR2(500),
	-- FOREIGN KEY ���� (id -> user ���̺��� id ����)--
	CONSTRAINT fk_review_id foreign key(id) references user(id),
	-- FOREIGN KEY ���� (bookingnum -> booking ���̺��� bookingnum ����)--
	CONSTRAINT fk_review_bookingnum foreign key(bookingnum) references booking(bookingnum) 
);


--�����̺� ������ȣ ����������--

CREATE SEQUENCE sq_parkingmate_idx START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE sq_mate_paycheck_idx START WITH 100 INCREMENT BY 1;

CREATE SEQUENCE sq_parkinglot_idx START WITH 200 INCREMENT BY 1;

CREATE SEQUENCE sq_community_idx START WITH 300 INCREMENT BY 1;

CREATE SEQUENCE sq_comment_idx START WITH 400 INCREMENT BY 1;

