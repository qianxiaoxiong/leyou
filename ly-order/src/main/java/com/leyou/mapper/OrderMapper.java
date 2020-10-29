package com.leyou.mapper;

import com.leyou.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);
//"insert into tb_order values (#{ record.record.totalPay}, #{ record.record.actualPay}, #{ record.record.promotionIds}
//        )"
//    @Insert("insert into tb_order values(#{record.orderId,jdbcType=BIGINT} #{ record.totalPay,jdbcType=BIGINT}, #{ record.actualPay,jdbcType=BIGINT}, #{ record.promotionIds,jdbcType=VARCHAR}, \n" +
//            "      #{ record.paymentType,jdbcType=BOOLEAN},     #{ record.postFee,jdbcType=BIGINT},            #{ record.createTime,jdbcType=DATE , javaType=java.util.Date}, \n" +
//            "      #{ record.shippingName,jdbcType=VARCHAR},    #{ record.shippingCode,jdbcType=VARCHAR},     #{ record.userId,jdbcType=BIGINT, javaType=long}, \n" +
//            "      #{ record.buyerMessage,jdbcType=VARCHAR},    #{ record.buyerNick,jdbcType=VARCHAR},        #{ record.buyerRate,jdbcType=BOOLEAN}, \n" +
//            "      #{ record.receiverState,jdbcType=VARCHAR},   #{ record.receiverCity,jdbcType=VARCHAR},     #{ record.receiverDistrict,jdbcType=VARCHAR}, \n" +
//            "      #{ record.receiverAddress,jdbcType=VARCHAR}, #{ record.receiverMobile,jdbcType=VARCHAR},    #{ record.receiverZip,jdbcType=VARCHAR}, \n" +
//            "      #{ record.receiver,jdbcType=VARCHAR},        #{ record.invoiceType,jdbcType=INTEGER},     #{ record.sourceType,jdbcType=INTEGER}" +
//            ")")
@Insert("insert into tb_order values( ${record.orderId}, ${record.totalPay}, ${record.actualPay} , null, \n" +
        "     ${record.paymentType}     ,    ${record.postFee}     ,         ' ${ \"2020-10-09 \" }' , \n" +
        "    null    ,   null ,    ${record.userId}, \n" +
        "     null   ,   ' ${record.buyerNick} '   ,       ${record.buyerRate} , \n" +
        "    ' ${record.receiverState} '  ,  ' ${record.receiverCity} ' ,   ' ${record.receiverDistrict} ' , \n" +
        "    '${record.receiverAddress}' , ${record.receiverMobile} ,  ${record.receiverZip} , \n" +
        "    ' ${record.receiver} '       ,  ${ record.invoiceType}  ,     ${record.sourceType}" +
        ")")
    @Options(useGeneratedKeys =true, keyProperty = "orderId" ,keyColumn = "order_id")
    int insertTest(@Param("record") Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}