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


SELECT m.*, b.*, pl.*
FROM mate_paycheck m
JOIN booking b ON m.car_num = b.bookingcarnum
JOIN parkinglot pl ON b.idx = pl.idx
WHERE m.mid = 'user2';


SELECT m.*, b.*, pl.*
    FROM mate_paycheck m
    JOIN booking b ON m.car_num = b.bookingcarnum
    JOIN parkinglot pl ON b.idx = pl.idx
    WHERE m.mid = #{mateId} AND m.status = '정산대기';