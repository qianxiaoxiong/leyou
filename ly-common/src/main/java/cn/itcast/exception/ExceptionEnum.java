package cn.itcast.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum ExceptionEnum {
    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_FOUND(400,"商品品牌未发现" ),
    BRANDINSERTFAIL(404,"品牌新增失败" ), CATEGORYBRADINSERTFAIL(400, " 分类品牌中间表插入失败"),
    INVALID_FILE_TYPE(400, "无效的文件类型" ), FILE_UPLOAD_FAIL(400,"图片校验失败" ),
    REQUESTARGSEXCEPTION(400,"请求参数异常"),REGIRSTERFAIL(400,"注册失败"),
    WRITEERROR(400,"写入数据库错误"), BADUSERNAMEORPASSWORD(400,"用户名或密码错误" ),
    SPEC_PARAM_NOT_FOUND(400,"参数没发现" ), GOODS_SAVE_ERROR(400,"保存商品失败" ),
    GOODS_NOT_FOUND(400,"GOODS_NOT_FOUND"), CART_NOT_FOUND(400," CART_NOT_FOUND" ),
    INSERT_ORDER_ERROR(400," INSERT_ORDER_ERROR" ), STOCK_NOT_ENOUGH(400,"STOCK_NOT_ENOUGH" ),
    ORDER_STATUS_ERROR(400, "ORDER_STATUS_ERROR"), WX_PAY_ORDER_FAIL(400, "WX_PAY_ORDER_FAIL"),
    WX_PAY_SIGN_INVALID(400,"WX_PAY_SIGN_INVALID" ), ORDER_NOT_FOUND(400,"ORDER_NOT_FOUND" ),
    ORDER_DETAIL_NOT_FOUND(400,"ORDER_DETAIL_NOT_FOUND" ), ORDER_STATUS_NOT_FOUND(400,"ORDER_STATUS_NOT_FOUND" );
    public   Integer value;
     public  String msg;


       ExceptionEnum(Integer value, String msg) {
        this.value =value;
        this.msg =msg;
      }


    public void setValue(Integer value) {
        this.value = value;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }


}
