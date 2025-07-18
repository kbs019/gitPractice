package com.ex.gitprac.repository.rec;

import com.ex.gitprac.data.rec.RecDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

@Repository
@Mapper
public interface RecMapper {

    public void insertRec(RecDTO rec);

    public List<RecDTO> findAll();

    public List<RecDTO> findFiltered(
        @Param("petNo") Integer petNo,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("categoryGroup") String categoryGroup
    );

    public RecDTO findByNo(@Param("recNo") int recNo);
}
