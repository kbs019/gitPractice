package com.ex.gitprac.repository.pet;

import com.ex.gitprac.data.pet.PetDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Mapper
@Repository
public interface PetMapper {
    List<PetDTO> findByUserId(@Param("userId") String userId);
}