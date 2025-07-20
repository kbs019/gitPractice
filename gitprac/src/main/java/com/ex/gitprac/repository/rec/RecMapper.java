package com.ex.gitprac.repository.rec;

import com.ex.gitprac.data.rec.RecDTO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RecMapper {

    List<RecDTO> findAll();

    List<RecDTO> findFiltered(
        @Param("petNo") Integer petNo,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("categoryGroup") String categoryGroup
    );

    RecDTO findByNo(int recNo);

    void insertRec(RecDTO rto);

    int updateRec(RecDTO recDTO);

    int deleteRec(int recNo);

    // ✅ 새로 추가된 메서드들 (완성본에서 반드시 포함)
    List<RecDTO> findAllByWriter(@Param("writer") String writer);

    List<RecDTO> findFilteredByWriter(
        @Param("writer") String writer,
        @Param("petNo") Integer petNo,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("categoryGroup") String categoryGroup
    );
}
