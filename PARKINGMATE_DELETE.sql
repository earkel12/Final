-- 단일 테이블 삭제
DROP TABLE IF EXISTS user;

-- 여러 테이블을 한꺼번에 삭제
DROP TABLE IF EXISTS
  review,
  mate_paycheck,
  user_cars,
  car_type,
  comments,
  community,
  parkingmate,
  booking,
  parkinglot,
  ask;

-- 시퀀스용 테이블 삭제
DROP TABLE IF EXISTS
  sq_comment_idx,
  sq_community_idx,
  sq_parkinglot_idx,
  sq_mate_paycheck_idx,
  sq_parkingmate_idx;
  
SHOW TABLES;
