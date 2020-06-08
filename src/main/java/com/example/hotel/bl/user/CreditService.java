package com.example.hotel.bl.user;

import com.example.hotel.po.Credit;
import com.example.hotel.vo.*;

import java.util.List;

public interface CreditService {
    /**
     * 获取用户信用记录
     * @param userId
     * @return
     */
    List<Credit> retrieveCreditRecord(Integer userId);
}
