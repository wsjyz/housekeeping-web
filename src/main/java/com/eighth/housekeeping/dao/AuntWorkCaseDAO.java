package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.AuntWorkCase;

import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
public interface AuntWorkCaseDAO {

    List<AuntWorkCase> findCaseByAuntId(String auntId);

    AuntWorkCase findCaseById(String caseId);
}
