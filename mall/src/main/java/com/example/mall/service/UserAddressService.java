package com.example.mall.service;

import com.example.mall.entity.UserAddress;

import java.util.List;

public interface UserAddressService {
    UserAddress addAddress(UserAddress address);

    List<UserAddress> getUserAddresses(Long userId);

    void deleteAddress(Long addressId, Long userId);

    UserAddress updateAddress(UserAddress address);

    void setDefaultAddress(Long addressId, Long userId);

    UserAddress getDefaultAddress(Long userId);
}
