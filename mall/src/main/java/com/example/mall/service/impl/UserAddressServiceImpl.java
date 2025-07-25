package com.example.mall.service.impl;

import com.example.mall.entity.UserAddress;
import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.repository.UserAddressRepository;
import com.example.mall.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressRepository addressRepository;

    @Override
    public UserAddress addAddress(UserAddress address) {
        return addressRepository.save(address);
    }

    @Override
    public List<UserAddress> getUserAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId, Long userId) {
        addressRepository.deleteByIdAndUserId(addressId, userId);
    }

    @Override
    @Transactional
    public UserAddress updateAddress(UserAddress address) {
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void setDefaultAddress(Long addressId, Long userId) {//设置新的默认地址
        // 取消原来的默认地址
        List<UserAddress> addresses = addressRepository.findByUserId(userId);
        addresses.forEach(addr -> {// 如果是默认地址，则取消默认状态
            if (addr.getIsDefault()) {// 如果当前地址是默认地址
                addr.setIsDefault(false);// 将其设置为非默认地址
                addressRepository.save(addr);// 保存更新后的地址（取消默认状态）
            }
        });

        // 设置新的默认地址
        UserAddress target = addressRepository.findById(addressId)
                .orElseThrow(() -> new BizException(ResultCode.ADDRESS_NOT_FOUND));
        if (!target.getUserId().equals(userId)) {
            throw new BizException(ResultCode.ADDRESS_UNAUTHORIZED);
        }
        target.setIsDefault(true);
        addressRepository.save(target);
    }

    @Override
    public UserAddress getDefaultAddress(Long userId) {
        return addressRepository.findByUserIdAndIsDefaultTrue(userId)
                .orElseThrow(() -> new BizException(ResultCode.DEFAULT_ADDRESS_NOT_FOUND));
    }
}
