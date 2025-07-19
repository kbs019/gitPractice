package com.ex.gitprac.data.diary;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PetDTO {

    private int petNo;
    private String id;
    private String petName;
    private String petBreed;
    private int petAge;
    private int petSize;
    private int petWeight;
    private LocalDate petBirth;
}
/* 
CREATE TABLE PET(

    PET_NO NUMBER PRIMARY KEY,
    WRITER VARCHAR2(50),
    PET_NAME VARCHAR2(50),
    PET_BREED VARCHAR2(50),
    PET_AGE NUMBER,
    PET_SIZE NUMBER,
    PET_WEIGHT NUMBER,
    PET_BIRTH DATE
);

CREATE SEQUENCE PET_SEQ NO CACHE;

SELECT * FROM PET;

INSERT INTO PET VALUES(PET_SEQ_NEXTVAL, ?, ?, ?, ?, ?, ?, ?);

DROP TABLE PET;

DROP SEQUENCE PET_SEQ;

COMMIT;
*/

