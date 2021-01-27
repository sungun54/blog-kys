# 캐릭터SET 설정
SET NAMES utf8mb4;

# DB 생성
DROP DATABASE IF EXISTS site21;
CREATE DATABASE site21;
USE site21;

# 카테고리
DROP TABLE IF EXISTS cateItem;
CREATE TABLE cateItem(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    `name` CHAR(100) NOT NULL UNIQUE    
);

TRUNCATE cateItem;

insert into cateItem
set `name` = '일상',
regDate = now();

INSERT INTO cateItem
SET `name` = 'IT자바기초',
regDate = NOW();

INSERT INTO cateItem
SET `name` = 'DB',
regDate = NOW();

INSERT INTO cateItem
SET `name` = '포트폴리오',
regDate = NOW();

INSERT INTO cateItem
SET `name` = '자바 기초 알고리즘',
regDate = NOW();

select * from cateItem;

drOp table if exists Article;

create table Article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate datetime not null,
    cateItemId int(10) unsigned not null,
    displayStatus tinyint(1) unsigned not null,
    `title` char(200) not null,
    `body` longtext not null,
    hit int(255) UNSIGNED NOT NULL,
    memberId = not null
);

DROP TABLE IF EXISTS `Member`;

CREATE TABLE `Member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME not null,
    `name` char(100) not null,
    loginId char(100) not null unique,
    loginPw char(255) not null,
    nickname char(100) not null,
    email char(200) not null
 );
 
DROP TABLE IF EXISTS `ArticleReply`; 
 
CREATE TABLE ArticleReply(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    articleId int(10) UNSIGNED NOT NULL,
    memberId int(10) UNSIGNED NOT NULL,
    `body` text not null
 ); 
 

select *
from `Member`

TRUNCATE `Member`;

truncate Article;

INSERT INTO Article
SET regDate = now(),
updateDate = NOW(),
cateItemId = 2,
title = CONCAT('제목', RAND()),
`body` = CONCAT('제목', RAND())  
 
INSERT INTO Article
SET regDate = NOW(),
updateDate = NOW(),
cateItemId = 2,
displayStatus = 1,
title ="jdbc란?",
`body` ="# JDBC(Java Database Connectivity)
- 자바에서 데이터베이스에 접속할 수 있도록 하는 자바 API이다.
- JDBC는 데이터베이스에서 자료를 쿼리하거나 업데이트하는 방법을 제공한다.
# JDBC Driver
- DBMS와 통신을 담당하는 자바 클래스이다.
- DMBS 별로 알맞은 JDBC 드라이버 필요하다. (jar)
- MY SQL, 오라클 MSSQL  등이 있다.";   

INSERT INTO Article
SET regDate = NOW(),
updateDate = NOW(),
cateItemId = 5,
displayStatus = 1,
title ="",
`body` ="";   

select *
from Article
ORDER BY id DESC

SELECT COUNT(*) AS cnt
FROM Article
WHERE displayStatus = 1
AND cateItemId = 2
AND id > 7

UPDATE Article
SET title = "안녕"    
WHERE id = 8