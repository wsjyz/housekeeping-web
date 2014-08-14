package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.OrderDAO;
import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dam on 2014/7/29.
 */
@Repository("OrderDAO")
public class OrderDAOImpl extends BaseDAO implements OrderDAO {
    @Override
    public void saveUserOrder(final AuntOrder order) {
        StringBuilder sql = new StringBuilder("insert into t_aunt_order " +
                "(order_id,order_use,work_time,work_length," +
                "address,description,unit_price,total_price,actual_price,use_coupon_count,floor_space," +
                "order_status,special_need,contact_way,aunt_id,user_id,corp_id,opt_time)");
        sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,order.getOrderId());
                ps.setString(2,order.getOrderUse());
                ps.setString(3,order.getWorkTime());
                ps.setInt(4, order.getWorkLength());
                ps.setString(5, order.getAddress());
                ps.setString(6,order.getDescription());
                ps.setBigDecimal(7, order.getUnitPrice());
                ps.setBigDecimal(8,order.getTotalPrice());
                ps.setBigDecimal(9,order.getActualPrice());
                ps.setInt(10, order.getUseCouponCount());
                ps.setInt(11, order.getFloorSpace());
                ps.setString(12, order.getOrderStatus());
                ps.setString(13,order.getSpecialNeed());
                ps.setString(14,order.getContactWay());
                ps.setString(15,order.getAuntId());
                ps.setString(16,order.getUserId());
                ps.setString(17,order.getCorpId());
                ps.setString(18,order.getOptTime());
            }
        });

    }

    @Override
    public OpenPage<AuntOrder> findOrderList(String memberId, String orderType,OpenPage<AuntOrder> page) {
        List<Object> params = new ArrayList<Object>();
        params.add(memberId);
        StringBuilder sql = new StringBuilder("select * from t_aunt_order where user_id = ?");
        StringBuilder countSql = new StringBuilder("select count(*) from t_aunt_order where user_id = ? ");
        if(!orderType.equals("ALL")){
            countSql.append("and order_status =?");
            sql.append("and order_status =?");
            params.add(orderType);
        }
        sql.append("limit ?,?");
        countSql.append("limit ?,?");
        params.add(page.getPageSize() * (page.getPageNo() - 1));
        params.add(page.getPageSize());

        List<AuntOrder> orderList = getJdbcTemplate().query(sql.toString(), params.toArray(),new AuntOrderRowMapper());
        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),params.toArray(),Integer.class);
        OpenPage<AuntOrder> orderOpenPage = new OpenPage<AuntOrder>();
        orderOpenPage.setTotal(count);
        orderOpenPage.setRows(orderList);
        return orderOpenPage;
    }

    @Override
    public OpenPage<AuntOrder> findAuntOrderList(String auntId, String orderType, OpenPage<AuntOrder> page) {
        List<Object> params = new ArrayList<Object>();
        params.add(auntId);
        StringBuilder sql = new StringBuilder("select * from t_aunt_order where aunt_id = ?");
        StringBuilder countSql = new StringBuilder("select count(*) from t_aunt_order where aunt_id = ? ");
        if(!orderType.equals("ALL")){
            countSql.append("and order_status =?");
            sql.append("and order_status =?");
            params.add(orderType);
        }
        sql.append("limit ?,?");
        countSql.append("limit ?,?");
        params.add(page.getPageSize() * (page.getPageNo() - 1));
        params.add(page.getPageSize());

        List<AuntOrder> orderList = getJdbcTemplate().query(sql.toString(), params.toArray(),new AuntOrderRowMapper());
        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),params.toArray(),Integer.class);
        OpenPage<AuntOrder> orderOpenPage = new OpenPage<AuntOrder>();
        orderOpenPage.setTotal(count);
        orderOpenPage.setRows(orderList);
        return orderOpenPage;
    }

    @Override
    public AuntOrder findOrderById(String orderId) {
        StringBuilder sql = new StringBuilder("");
        sql.append("select * from t_aunt_order where order_id = ?");
        List<AuntOrder> orderInfoList = getJdbcTemplate().query(sql.toString(),new String[]{orderId},new AuntOrderRowMapper());
        if(!CollectionUtils.isEmpty(orderInfoList) ){
            AuntOrder auntInfo = orderInfoList.get(0);
            return auntInfo;
        }
        return null;
    }

    @Override
    public void deleteOrder(String memberId, String orderId) {
        StringBuilder sql = new StringBuilder("");
        sql.append("delete from t_aunt_order where user_id = ? and order_id = ?");
        getJdbcTemplate().update(sql.toString(),new String[]{memberId,orderId});
    }

    @Override
    public void deleteOrderBatch(final String memberId, final String... orderIds) {
        StringBuilder sql = new StringBuilder("");
        sql.append("delete from t_aunt_order where user_id = ? and order_id = ?");
        getJdbcTemplate().batchUpdate(sql.toString(),new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1,memberId);
                ps.setString(2,orderIds[i]);
            }

            @Override
            public int getBatchSize() {
                return orderIds.length;
            }
        });
    }

    @Override
    public int findOrderCountsByMemberIdAndType(String memberId,String orderType){
        StringBuilder countSql = new StringBuilder("select count(*) from t_aunt_order where user_id = ? " +
                "and order_status =?");
        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),new String[]{memberId,orderType},Integer.class);
        return count;
    }

    public class AuntOrderRowMapper implements RowMapper<AuntOrder>{

        @Override
        public AuntOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            AuntOrder order = new AuntOrder();
            order.setUserId(rs.getString("user_id"));
            order.setOptTime(rs.getString("opt_time"));
            order.setActualPrice(rs.getBigDecimal("actual_price"));
            order.setAddress(rs.getString("address"));
            order.setAuntId(rs.getString("aunt_id"));
            order.setContactWay(rs.getString("contact_way"));
            order.setDescription(rs.getString("description"));
            order.setFloorSpace(rs.getInt("floor_space"));
            order.setOrderId(rs.getString("order_id"));
            order.setOrderStatus(rs.getString("order_status"));
            order.setOrderUse(rs.getString("order_use"));
            order.setSpecialNeed(rs.getString("special_need"));
            order.setTotalPrice(rs.getBigDecimal("total_price"));
            order.setUnitPrice(rs.getBigDecimal("unit_price"));
            order.setUseCouponCount(rs.getInt("use_coupon_count"));
            order.setWorkLength(rs.getInt("work_length"));
            order.setWorkTime(rs.getString("work_time"));
            order.setCorpId(rs.getString("corp_id"));
            return order;
        }
    }
}
