package com.eighth.housekeeping.dao;


import java.util.List;

import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.OpenPage;


/**
 * Created by dam on 2014/7/28.
 */
public interface CorpDAO {
    OpenPage<Corp> findCorpPage(String corpName,String loginName,OpenPage page);
    void saveCorp(Corp corp);
    Corp findCorpId(String corpId);
    String updateCorp(Corp corp);
    String deleteCorp(String corpId);
	Corp login(String loginName, String md5Psw);
	List<Corp> corpList();
}
