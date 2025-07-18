package com.ex.gitprac.service.rec;

import com.ex.gitprac.data.rec.RecDTO;
import com.ex.gitprac.repository.rec.RecMapper;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RecService {

    private final RecMapper recMapper;

    public RecService(RecMapper recMapper) {
        this.recMapper = recMapper;
    }

    public void save(RecDTO recDTO) {
        recMapper.insertRec(recDTO);
    }

    public List<RecDTO> getAllRecs() {
        return recMapper.findAll();
    }

    public List<RecDTO> getFilteredRecs(Integer petNo, String startDate, String endDate, String categoryGroup) {
        return recMapper.findFiltered(petNo, startDate, endDate, categoryGroup);
    }
}
