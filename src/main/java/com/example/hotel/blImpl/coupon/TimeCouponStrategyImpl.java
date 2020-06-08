package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种限时优惠策略 ( 限时：节日 )
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {        // 传入的coupon为已筛选为本hotel的coupon

        try {
            // 自行添加了 coupon 是否 有效 的判断
            if (coupon.getStatus() == 1 && coupon.getCouponType() == 4) {         // 有效 + 种类
                //if(coupon.getStartTime()<=orderVO.getCreateDate() && coupon.getEndTime()>=orderVO.getCreateDate()){           //时间
                //可以试着全部转为8位long来比较大小
                Long start = Timestamp.valueOf(coupon.getStartTime()).getTime();
                Long end = Timestamp.valueOf(coupon.getEndTime()).getTime();
                String temp_time = orderVO.getCreateDate();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Long time=sf.parse(temp_time).getTime();
                //Long time = Long.valueOf(temp_time.substring(0, 4)) * 1000000000 + Integer.valueOf(temp_time.substring(5, 7)) * 10000000 + Integer.valueOf(temp_time.substring(8, 10)) * 100000;

                //也可以试着将String类型的createDate转换为localDateTime来比较

                if (start <= time && end >= time) {
                    if (orderVO.getPrice() >= coupon.getTargetMoney()) {          //门槛
                        return true;
                    }
                }
            }

            return false;
        }
        catch (Exception e){
            return false;
        }
    }
}
