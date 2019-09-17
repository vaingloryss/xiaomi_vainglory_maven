package com.vainglory.dao.daoImpl;

import com.vainglory.dao.IOrderDetailDao;
import com.vainglory.domain.OrderDetail;
import com.vainglory.utils.DataSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class OrderDetailDaoImpl implements IOrderDetailDao {
    private QueryRunner queryRunner = new QueryRunner();
    @Override
    public void add(OrderDetail orderDetail) {
        Object[] params = {orderDetail.getOid(),orderDetail.getPid(),orderDetail.getNum(),orderDetail.getMoney()};
        String sql = "insert into tb_orderdetail(oid,pid,num,money) values(?,?,?,?)";
        try {
            queryRunner.update(DataSourceUtils.getConnection(),sql,params);
        } catch (SQLException e) {
            log.error("订单详情添加失败："+e.getMessage());
        }
    }
    @Override
    public List<OrderDetail> findByOid(String id) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from tb_orderdetail where oid=?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(OrderDetail.class),id);
        } catch (SQLException e) {
            log.error("根据订单ID查询失败："+e.getMessage());
        }
        return null;
    }
}
