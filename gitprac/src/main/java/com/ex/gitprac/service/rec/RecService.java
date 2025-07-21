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

    // 작성자 기반 전체 조회 + 페이징
    public List<RecDTO> getRecListWithPaging(String writer, int offset, int limit) {
        List<RecDTO> all = recMapper.findAllByWriter(writer); // ← 작성자 기준으로 전체 조회

        if (offset >= all.size()) {
            return new ArrayList<>();
        }

        int end = Math.min(offset + limit, all.size());
        return all.subList(offset, end);
    }

    // 작성자 기반 필터링 + 페이징
    public List<RecDTO> getRecListFilteredWithPaging(String writer, Integer petNo, String startDate, String endDate,
                                                    String categoryGroup, int offset, int limit) {
        List<RecDTO> filteredList = recMapper.findFilteredByWriter(writer, petNo, startDate, endDate, categoryGroup); // ← 수정

        if (offset >= filteredList.size()) {
            return new ArrayList<>();
        }

        int end = Math.min(offset + limit, filteredList.size());
        return filteredList.subList(offset, end);
    }

}
