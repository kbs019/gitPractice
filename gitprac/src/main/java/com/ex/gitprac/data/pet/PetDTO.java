package com.ex.gitprac.data.pet;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate petBirth;
}
/* 
CREATE TABLE PET(

    petNo NUMBER PRIMARY KEY,
    id VARCHAR2(50),
    petName VARCHAR2(50),
    petBreed VARCHAR2(50),
    petAge NUMBER,
    petSize NUMBER,
    petWeight NUMBER,
    petBirth DATE
);

CREATE SEQUENCE PET_SEQ NOCACHE;

SELECT * FROM PET;

INSERT INTO PET VALUES(PET_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?);

DROP TABLE PET;

DROP SEQUENCE PET_SEQ;

COMMIT;
*/

