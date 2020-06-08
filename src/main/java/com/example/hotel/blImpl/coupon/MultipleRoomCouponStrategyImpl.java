package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class MultipleRoomCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种 多间优惠
     * @param orderVO
     * @param coupon
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {            // 传入的coupon为已筛选为本hotel的coupon

        try {
            if (coupon.getStatus() == 1 && coupon.getCouponType() == 2) {     // 有效 + 种类

                //考虑要不要考虑时间
                if (orderVO.getRoomNum() >= 3) {
                    return true;
                }
            }

            return false;
        }
        catch (Exception e){
            return false;
        }
    }
}
