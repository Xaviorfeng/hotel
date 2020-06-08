package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.CreditService;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.po.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditMapper creditMapper;

    @Override
    public List<Credit> retrieveCreditRecord(Integer userId) {
        return creditMapper.selectByUserId(userId);
    }
}
