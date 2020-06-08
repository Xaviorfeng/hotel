package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.CreditMapper;
import com.example.hotel.po.Credit;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String CREDIT_LACK = "信用值太低";
    private final static String ANNUL_ERROR = "撤销失败";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;

    //personal 用于修改credit
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CreditMapper creditMapper;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {

            // 转移了位置
            User user = accountService.getUserInfo(orderVO.getUserId());
            if(user.getCredit()<0){
                return ResponseVO.buildFailure(CREDIT_LACK);
            }

            //修改时间为 yyyy-MM-dd HH:mm:ss
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            //User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);            // 通过这个方法将orderVo的属性复制到order
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());       //更新房间占用情况(更新数据库)
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互） 注意除了对order状态的修改之外还有可能涉及和别的业务的交互
        //personal

        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curdate = new Date(System.currentTimeMillis());
            String cancellationDate = sf.format(curdate);

            orderMapper.annulOrder(orderid, cancellationDate);        //对order状态的修改

            Order order = orderMapper.getOrderById(orderid);
            hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());        //恢复占用的房间数

            // 由于原来的orderlist数据库没有cancellationDate属性，所以在hotel.sql里自行添加了cancellationDate属性，需要确认能run

            String inDate = order.getCheckInDate() + " 12:00:00";
            if ( sf.parse(inDate).getTime() - curdate.getTime() < 21600000) {       // 6小时
                accountMapper.updateCredit(order.getUserId(), order.getPrice() * 0.5);    // try
                User user=accountService.getUserInfo(order.getUserId());

                // ************需要在信用系统中留下记录！！！！！！！！！！！！！！！！！！！！******************************
                Credit credit=new Credit();
                credit.setActionType(3);
                credit.setCreditChange(-order.getPrice() * 0.5);
                credit.setCreditResult(user.getCredit());
                credit.setOrderId(orderid);
                credit.setUserId(order.getUserId());
                credit.setChangeTime(curdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()); //感觉怪怪的 好像时间不一样（不同步
                creditMapper.addCreditRecord(credit);
                //
            }

            return ResponseVO.buildSuccess(true);
        }
        catch (Exception e){            // 这种情况出现感觉算是异常订单了，因为修改了数据但是却返回失败？？？？？？？？？？？？？？可以试着还原数据?或者把订单置为异常
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
    }

    //change
    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }
}
