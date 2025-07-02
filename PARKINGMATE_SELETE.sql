#테이블보기
-- 현재 모든 테이블 보기--
SHOW TABLES;
-- 각테이블 보기--
SELECT * FROM review;
SELECT * FROM mate_paycheck;
SELECT * FROM user_cars;
SELECT * FROM comment;
SELECT * FROM community;
SELECT * FROM ask;
SELECT * FROM booking;
SELECT * FROM parkingmate;
SELECT * FROM parkinglot;
SELECT * FROM car_type;
SELECT * FROM user;
SELECT * FROM notice;
SELECT * FROM notice_poto;
SELECT * FROM faq;


SELECT b.*, p.*, pl.*
		FROM booking b
		JOIN mate_paycheck p ON
		b.bookingcarnum = p.car_num
		JOIN parkinglot pl ON b.idx = pl.idx
		WHERE
		p.status = '정산대기';