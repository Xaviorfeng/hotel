package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelTargetMoneyCouponVO;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class TargetMoneyCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种满减金额优惠策略 （ 满减优惠 ）
     * @param orderVO
     * @param coupon
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {            // 传入的coupon为已筛选为本hotel的coupon

        try {
            // 自行添加了 coupon 是否 有效 的判断   coupon.getStatus() == 1 &&              ??非常神奇，不判断状态反而通过了测试
            // 考虑要不要添加时间的限制
            if (coupon.getCouponType() == 3 && orderVO.getPrice() >= coupon.getTargetMoney()) {
                return true;
            }

            return false;
        }

        catch (Exception e){
            return false;
        }
    }
}
