package com.vainglory.service.serviceImpl;

import com.vainglory.dao.ICartDao;
import com.vainglory.dao.IGoodsDao;
import com.vainglory.dao.daoImpl.DaoFactory;
import com.vainglory.domain.Cart;
import com.vainglory.domain.Goods;
import com.vainglory.service.ICartService;

import java.util.List;

public class CartServiceImpl implements ICartService {

    private ICartDao cartDao = DaoFactory.getCartDao();
    private IGoodsDao goodsDao = DaoFactory.getGoodsDao();

    @Override
    public Cart findByUidAndGid(Integer uid, Integer gid) {
        return cartDao.findByUidAndGid(uid,gid);
    }

    @Override
    public boolean updateCart(Cart cart) {
        int result = cartDao.update(cart);
        return result == 1;
    }

    @Override
    public boolean addCart(Cart cart) {
        int result = cartDao.add(cart);
        return result == 1;
    }

    @Override
    public List<Cart> finByUid(Integer uid) {
        List<Cart> carts = cartDao.finByUid(uid);
        if (carts !=null){
            for (Cart cart : carts) {
                Goods goods = goodsDao.findById(cart.getGid());
                cart.setGoods(goods);
            }
        }
        return carts;
    }

    @Override
    public boolean deleteCart(Integer uid, Integer gid) {
        int result = cartDao.deleteByUidAndGid(uid,gid);
        return result==1;
    }

    @Override
    public void clearCart(Integer uid) {
        cartDao.deleteByUid(uid);
    }
}
