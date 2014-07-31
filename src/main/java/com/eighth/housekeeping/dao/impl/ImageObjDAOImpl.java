package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.ImageObjDAO;
import com.eighth.housekeeping.domain.ImageObj;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("ImageObjDAO")
public class ImageObjDAOImpl extends BaseDAO implements ImageObjDAO {
    @Override
    public List<ImageObj> findImageObjByObjIdAndType(String objId, String imageType) {
        StringBuilder imgSql = new StringBuilder("");
        imgSql.append("select * from t_img_obj where obj_id = ? and image_type =?");
        List<ImageObj> caseList = getJdbcTemplate().query(imgSql.toString(),new String[]{objId,imageType},new ImageObjMapper());
        return caseList;
    }
    public class ImageObjMapper implements RowMapper<ImageObj>{

        @Override
        public ImageObj mapRow(ResultSet rs, int rowNum) throws SQLException {
            ImageObj obj = new ImageObj();
            obj.setHpixel(rs.getInt("hpixel"));
            obj.setWpixel(rs.getInt("wpixel"));
            obj.setImageId(rs.getString("image_id"));
            obj.setObjId(rs.getString("obj_id"));
            obj.setImageType(rs.getString("image_type"));
            return obj;
        }
    }
}
