package cn.itcast.config;

import cn.itcast.utils.CookieUtils;
import com.leyou.entity.UserInfo;
import com.leyou.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@EnableConfigurationProperties({JwtProperties.class,FilterProperties.class})
public class  LoginFilter extends ZuulFilter {

    @Autowired
    private  JwtProperties jwtProperties;
    @Autowired
    private  FilterProperties filterProperties;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        //获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //request
        HttpServletRequest request = ctx.getRequest();
        //获取路径
        String requestURI = request.getRequestURI();

        return !isAllowPath(requestURI);
    }
    public Boolean isAllowPath(String requestURI){
        Boolean flag =false;
        for(String path: this.filterProperties.getAllowPaths()){
            if(requestURI.startsWith(path)){
                flag = true;
                break;
            }
        }
        return  flag;
    }

    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取requeset
        HttpServletRequest request = currentContext.getRequest();
        //获取token
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());

        try {
            //校验通过什么都不做 放行，    不通过失败
            JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            //不通过返回403
            e.printStackTrace();
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(403);
        }

        return null;
    }
}
