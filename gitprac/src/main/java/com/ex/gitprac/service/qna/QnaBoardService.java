package com.ex.gitprac.service.qna;

import org.springframework.stereotype.Service;

import com.ex.gitprac.repository.qna.QnaBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaBoardService {

    private final QnaBoardMapper qnaBoardMapper;
}
