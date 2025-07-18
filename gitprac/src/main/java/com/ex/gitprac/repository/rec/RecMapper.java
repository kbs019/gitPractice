package com.ex.gitprac.repository.rec;

import com.ex.gitprac.data.rec.RecDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

@Repository
@Mapper
public interface RecMapper {

    void insertRec(RecDTO rec);

    List<RecDTO> findAll();

    List<RecDTO> findFiltered(
        @Param("petNo") Integer petNo,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("categoryGroup") String categoryGroup
    );
}
