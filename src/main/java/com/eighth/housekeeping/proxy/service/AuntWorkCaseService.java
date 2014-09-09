package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.AuntWorkCase;

import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
public interface AuntWorkCaseService {

    List<AuntWorkCase> findCaseByAuntId(String auntId);

    AuntWorkCase findCaseById(String caseId);  
    void addWorkCase(AuntWorkCase auntWorkCase);
    String updateWorkCase(AuntWorkCase auntWorkCase);
    void deleteWorkCase(String auntId);
}
