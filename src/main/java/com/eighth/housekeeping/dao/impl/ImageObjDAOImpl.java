package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.ImageObjDAO;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.utils.CommonStringUtils;
import com.eighth.housekeeping.utils.Constants;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("ImageObjDAO")
public class ImageObjDAOImpl extends BaseDAO implements ImageObjDAO {
    @Override
    public List<ImageObj> findImageObjByObjIdAndType(String objId, String imageType) {
        StringBuilder imgSql = new StringBuilder("");
        imgSql.append("select * from t_img_obj where obj_id = ? and image_type =? order by opt_time desc");
        List<ImageObj> caseList = getJdbcTemplate().query(imgSql.toString(),new String[]{objId,imageType},new ImageObjMapper());
        return caseList;
    }
    public class ImageObjMapper implements RowMapper<ImageObj>{

        @Override
        public ImageObj mapRow(ResultSet rs, int rowNum) throws SQLException {
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            ImageObj obj = new ImageObj();
            obj.setHpixel(rs.getInt("hpixel"));
            obj.setWpixel(rs.getInt("wpixel"));
            obj.setImageId(rs.getString("image_id"));
            obj.setObjId(rs.getString("obj_id"));
            obj.setImageType(rs.getString("image_type"));
            obj.setOptTime(rs.getString("opt_time"));
            Calendar cal=Calendar.getInstance();
            int month=0;
            try {
				cal.setTime(sdf.parse(obj.getOptTime()));
				month=cal.get(Calendar.MONTH)+1;
				String url=obj.getImageType().toLowerCase()+"/"+month+"/"+obj.getImageId()+".jpg";
				obj.setImageUrl(url);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return obj;
        }
    }
	@Override
	public String saveImageObj(final ImageObj imageObj) {
		  StringBuilder sql = new StringBuilder("insert into t_img_obj " +
	                "(image_id,image_type,obj_id,corp_id,opt_time)");
	        sql.append("values(?,?,?,?,?)");
	        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps) throws SQLException {
	                ps.setString(1,imageObj.getImageId());
	                ps.setString(2,imageObj.getImageType());
	                ps.setString(3,imageObj.getObjId());
	                ps.setString(4,imageObj.getCorpId());
	                ps.setString(5,imageObj.getOptTime());
	            }
	        });
	        return imageObj.getImageId();
	}
	@Override
	public void deleteImageObj(String objId, String imageType) {
		  StringBuilder imgSql = new StringBuilder("");
	        imgSql.append("delete from  t_img_obj where obj_id = '"+objId+"' and image_type ='"+imageType+"'");
	    	getJdbcTemplate().update(imgSql.toString());
	}
	@Override
	public void deleteImageObjByImageId(String imageId) {
		  StringBuilder imgSql = new StringBuilder("");
	        imgSql.append("delete from  t_img_obj where image_id ='"+imageId+"'");
	    	getJdbcTemplate().update(imgSql.toString());
		
	}
}
