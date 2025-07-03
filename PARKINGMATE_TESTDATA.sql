#유저테이블
INSERT INTO user VALUES ('ROSAL', '1234', '김로사', 123, '12345', 'ㅇㄹㄷㅇ@BNA', 0, 0);
INSERT INTO user(id, pwd, name, resident_num, tel, email) VALUES('ROSA1', '12341', '김로사1', 1231, '123451', 'ㅇㄹㄷ1ㅇ@BNA');

#문의테이블
insert into ask values('ROSA1', '문의', 'ㅇㅇ', 'ㅇㅇㅇ', '', 'ㅇㅇ');

#주차장테이블
INSERT INTO parkinglot
  (name, addr, type, price, price2, time) VALUES
  ('주차장A','서울시 강남구','실내',3000,5000,'2025-06-18');

#주차내역테이블
INSERT INTO `booking` (
  `bookingnum`, `bookingdate`, `bookingcarnum`, `outime`, `idx`, `id`
) VALUES (
  'BKG0001', '2025-06-18', '12가1234', '2025-06-18 14:30:00',
200, 'ROSA1'
);

#주차장이용내역 예제
INSERT INTO `booking` (
  `bookingnum`, `bookingdate`, `bookingcarnum`, `outime`, `status`, `idx`, `id`
) VALUES (
  'BKG0002', '2025-06-18', '98가1234', '2025-06-18 14:30:00',
  '주차장결제완료', 200, '하루'
);

#주차대행이용내역 예제
INSERT INTO `booking` (
  `bookingnum`, `bookingdate`, `bookingcarnum`, `outime`, `status`, `instand`, `idx`, `id`
) VALUES (
  'BKG0003', '2025-06-18', '98가1234', '2025-06-18 14:30:00',
  '주차장결제완료', '1', 200, '하루'
);

#파킹메이트테이블
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

#커뮤니티테이블
INSERT INTO `community` (
  `id`, `title`, `content`
) VALUES (
  'ROSA1',
  '첫 번째 커뮤니티 글',
  '안녕하세요! 파킹메이트 커뮤니티에 오신 것을 환영합니다.'
);

#코멘트테이블
INSERT INTO `comment` (
  `idx`, `id`, `content`
) VALUES (
  300,       -- community.idx = 1번 글
  'ROSA1',
  '첫 댓글 남깁니다. 유용한 정보 감사합니다!'
);

#차유형테이블
INSERT INTO `car_type` (
  `modelname`, `brandname`
) VALUES (
  'Model S', 'Tesla'
);

#유저차량테이블
INSERT INTO `user_cars` (
  `car_num`, `model_year`, `gas_type`, `color`, `gear`, `id`, `modelname`
) VALUES (
  '12가3456', 2023, 'gasoline', 'Red', 'automatic', 'ROSA1', 'Model S'
);

insert into user_cars
values('11가1234', 2022, 'gas', 'Blue', 'auto', '하루', 'Model S');

#메이트정산테이블
INSERT INTO mate_paycheck (
  id, endtime, mid, car_num
) VALUES (
  'ROSA1',
  '2025-07-01 10:00:00',   -- 정산 종료 시각
  'ROSA1',
  '12가3456'
);

#리뷰테이블
INSERT INTO `review` (
  `id`, `bookingnum`, `content`
) VALUES (
  'ROSA1',
  'BKG0001',
  '예약 경험이 매우 만족스러웠습니다. 다시 이용할게요!'
);




