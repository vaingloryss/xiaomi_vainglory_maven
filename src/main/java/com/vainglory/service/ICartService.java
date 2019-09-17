package com.vainglory.service;

import com.vainglory.domain.Cart;

import java.util.List;

public interface ICartService {
    Cart findByUidAndGid(Integer uid, Integer gid);
    boolean updateCart(Cart cart);
    boolean addCart(Cart cart);
    List<Cart> finByUid(Integer uid);
    boolean deleteCart(Integer uid, Integer gid);
    void clearCart(Integer uid);
}
