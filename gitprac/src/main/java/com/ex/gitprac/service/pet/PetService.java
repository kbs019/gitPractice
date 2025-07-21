package com.ex.gitprac.service.pet;

import java.util.List;

import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.repository.pet.PetMapper;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetMapper petMapper;

    public List<PetDTO> getPetsByUserId(String userId) {
        return petMapper.findByUserId(userId);
    }
}
