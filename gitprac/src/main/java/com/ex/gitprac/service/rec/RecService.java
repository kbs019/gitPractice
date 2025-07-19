package com.ex.gitprac.service.rec;

import com.ex.gitprac.data.rec.RecDTO;
import com.ex.gitprac.repository.rec.RecMapper;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecService {

    private final RecMapper recMapper;

    public void save(RecDTO recDTO) {
        recMapper.insertRec(recDTO);
    }

    public List<RecDTO> getAllRecs() {
        return recMapper.findAll();
    }

    public List<RecDTO> getFilteredRecs(Integer petNo, String startDate, String endDate, String categoryGroup) {
        return recMapper.findFiltered(petNo, startDate, endDate, categoryGroup);
    }

    public RecDTO getRecByNo(int recNo) {
    return recMapper.findByNo(recNo);
}

    public int updateRec(RecDTO recDTO) {
        return recMapper.updateRec(recDTO);
    }

    public int deleteRec(int recNo) {
        return recMapper.deleteRec(recNo);
    }

    public List<RecDTO> getRecListWithPaging(int offset, int limit) {
    List<RecDTO> all = recMapper.findAll();

    if (offset >= all.size()) {
        return new ArrayList<>();
    }

    int end = Math.min(offset + limit, all.size());
    return all.subList(offset, end);
}

    public List<RecDTO> getRecListFilteredWithPaging(Integer petNo, String startDate, String endDate, String categoryGroup, int offset, int limit){
        List<RecDTO> filteredList = recMapper.findFiltered(petNo, startDate, endDate, categoryGroup);
        if (offset >= filteredList.size()) {
            return new ArrayList<>();
        }
        int end = Math.min(offset + limit, filteredList.size());
        return filteredList.subList(offset, end);
    }
}
