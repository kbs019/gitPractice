package com.ex.gitprac.data.diary;

import java.time.LocalDate;

import lombok.Data;
@Data
public class PetDTO {

    private int petNo;
    private String writer;
    private String dogBreed;
    private int dogAge;
    private int dogSize;
    private int dogWeight;
    private LocalDate dogBirth;
}
/* 
CREATE TABLE PET(

    PET_NO NUMBER PRIMARY KEY,
    WRITER VARCHAR2(50),
    DOG_BREED VARCHAR2(50),
    DOG_AGE NUMBER,
    DOG_SIZE NUMBER,
    DOG_WEIGHT NUMBER,
    DOG_BIRTH DATE
);

CREATE SEQUENCE PET_SEQ NO CACHE;

SELECT * FROM PET;

INSERT INTO PET VALUES(PET_SEQ_NEXTVAL, ?, ?, ?, ?, ?, ?);

DROP TABLE PET;

DROP SEQUENCE PET_SEQ;

COMMIT;
*/

