CREATE TABLE child (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    serial_num BIGINT UNSIGNED UNIQUE NOT NULL,
    phone_num VARCHAR(13) UNIQUE NOT NULL,
    name VARCHAR(15) NOT NULL,
    birth_date DATE NOT NULL,
    account_info VARCHAR(255) NOT NULL,
    profile_id TINYINT NOT NULL DEFAULT 1,
    score TINYINT UNSIGNED NOT NULL DEFAULT 50,
    CHECK (score >= 0 AND score <= 100)
);

CREATE TABLE parents (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    serial_num BIGINT UNSIGNED UNIQUE NOT NULL,
    phone_num VARCHAR(13) UNIQUE NOT NULL,
    name VARCHAR(15) NOT NULL,
    birth_date DATE NOT NULL,
    account_info VARCHAR(255) NOT NULL,
    profile_id TINYINT NOT NULL DEFAULT 1
);

CREATE TABLE family (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    child_sn BIGINT UNSIGNED NOT NULL,
    parents_sn BIGINT UNSIGNED NOT NULL,
    parents_alias VARCHAR(15), -- default 로 parents의 name
    CONSTRAINT fk_family_child_sn FOREIGN KEY (child_sn) REFERENCES child(serial_num) ON DELETE CASCADE,
    CONSTRAINT fk_family_parents_sn FOREIGN KEY (parents_sn) REFERENCES parents(serial_num) ON DELETE CASCADE,
    CONSTRAINT unique_family UNIQUE (child_sn, parents_sn)
);

DELIMITER //
CREATE PROCEDURE generate_serial_num(OUT serial_num BIGINT UNSIGNED)
BEGIN
    DECLARE random_num BIGINT UNSIGNED DEFAULT 0;
    DECLARE dupl_count INT DEFAULT 0;
    DECLARE attempt_count INT DEFAULT 0;

    REPEAT
        SET random_num = FLOOR(RAND() * 9999999999); -- 10자리 난수 생성
        SELECT COUNT(*) INTO dupl_count FROM child WHERE serial_num = random_num;
        IF dupl_count = 0 THEN
            SELECT COUNT(*) INTO dupl_count FROM parents WHERE serial_num = random_num;
        END IF;
        SET attempt_count = attempt_count + 1;
    UNTIL dupl_count = 0 OR attempt_count >= 1000 END REPEAT;

    IF dupl_count = 0 THEN
        SET serial_num = random_num;
    ELSE
        SET serial_num = NULL;
    END IF;
END//
DELIMITER ;

DELIMITER //
CREATE TRIGGER before_insert_family
BEFORE INSERT ON family
FOR EACH ROW
BEGIN
    DECLARE parent_name VARCHAR(15);

    IF NEW.parents_alias IS NULL THEN
        SELECT name INTO parent_name FROM parents WHERE serial_num = NEW.parents_sn;
        SET NEW.parents_alias = parent_name;
    END IF;
END //
DELIMITER ;