package com.example.mall.controller;

import com.example.mall.entity.UserAddress;
import com.example.mall.service.UserAddressService;
import com.example.mall.service.UserService;
import com.example.mall.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService addressService;
    private final UserService userService;

    @PostMapping("/add")
    public Result<UserAddress> add(@RequestBody UserAddress address) {
        address.setUserId(userService.getCurrentUserId());
        return Result.success(addressService.addAddress(address));
    }

    @GetMapping("/list")
    public Result<List<UserAddress>> list() {
        Long userId = userService.getCurrentUserId();
        return Result.success(addressService.getUserAddresses(userId));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Long userId = userService.getCurrentUserId();
        addressService.deleteAddress(id, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<UserAddress> update(@RequestBody UserAddress address) {
        address.setUserId(userService.getCurrentUserId());
        return Result.success(addressService.updateAddress(address));
    }

    @PostMapping("/default/{id}")
    public Result<Void> setDefault(@PathVariable Long id) {
        Long userId = userService.getCurrentUserId();
        addressService.setDefaultAddress(id, userId);
        return Result.success();
    }

    @GetMapping("/default")
    public Result<UserAddress> getDefault() {
        Long userId = userService.getCurrentUserId();
        return Result.success(addressService.getDefaultAddress(userId));
    }
}
