package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.ImageObj;

import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
public interface ImageObjDAO {

    List<ImageObj> findImageObjByObjIdAndType(String objId,String imageType );
    String saveImageObj(ImageObj imageObj);
    void deleteImageObj(String objId,String imageType);
	void deleteImageObjByImageId(String imageId);
}
