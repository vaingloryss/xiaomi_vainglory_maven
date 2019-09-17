package com.vainglory.service.serviceImpl;

import com.vainglory.dao.*;
import com.vainglory.dao.daoImpl.DaoFactory;
import com.vainglory.domain.Address;
import com.vainglory.domain.Cart;
import com.vainglory.domain.Order;
import com.vainglory.domain.OrderDetail;
import com.vainglory.service.IOrderService;
import com.vainglory.utils.DataSourceUtils;

import java.util.List;

public class OrderServiceImpl implements IOrderService {

    private ICartDao cartDao = DaoFactory.getCartDao();
    private IAddressDao addressDao = DaoFactory.getAddressDao();
    private IOrderDao orderDao  = DaoFactory.getOrderDao();
    private IOrderDetailDao orderDetailDao = DaoFactory.getOrderDetailDao();
    private IGoodsDao goodsDao = DaoFactory.getGoodsDao();

    @Override
    public List<Cart> getCarts(Integer uid) {
        return cartDao.finByUid(uid);
    }

    @Override
    public List<Address> getAddresses(Integer uid) {
        return addressDao.findByUid(uid);
    }

    @Override
    public void addOrder(Order order, List<OrderDetail> orderDetails) {
        try {
            //开启事务
            DataSourceUtils.startTransaction();
            //添加订单
            orderDao.add(order);
            //向订单详情中添加数据
            for (OrderDetail orderDetail : orderDetails) {
                orderDetailDao.add(orderDetail);
            }
            //清空购物车
            cartDao.deleteByUid(order.getUid());
            DataSourceUtils.commit();
        }catch (Exception e){
            try {
                DataSourceUtils.rollback();
            }catch (Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try{
               DataSourceUtils.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void updateOrderStatus(String oid,String status){
        orderDao.updateOrderStatus(oid,status);
    }

    @Override
    public List<Order> findOrderByUid(Integer id) {
        List<Order> orders = orderDao.findByUid(id);
        for (Order order : orders) {
            order.setAddress(addressDao.findById(order.getAid()));
        }
        return orders;
    }

    @Override
    public Order findOrderByOid(String oid) {
        Order order = orderDao.findByOid(oid);
        Address address = addressDao.findById(order.getAid());
        List<OrderDetail> orderDetails = orderDetailDao.findByOid(oid);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setGoods(goodsDao.findById(orderDetail.getPid()));
        }
        order.setAddress(address);
        order.setOrderDetails(orderDetails);
        return order;
    }
}
