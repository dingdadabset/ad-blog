package org.example.conf;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.example.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
/**
 * @description:这段代码是一个基于JWT的认证过滤器，主要用于在接受请求时，对请求头中的JWT Token进行验证和解析，并将解析出的用户信息存入到Spring Security上下文中，以便于后续的鉴权和授权操作。该过滤器实现了OncePerRequestFilter接口，用于保证在一次请求中只被执行一次。
 *
 * 具体来说，该过滤器会首先从请求头中获取token信息，如果没有token信息，则说明该接口不需要登录，直接放行。否则，将使用JwtUtil类对token进行解析，获取其中的userId信息，并从Redis缓存中获取对应的用户信息。如果获取不到用户信息，则说明用户登录已经过期，需要重新登录。如果获取到了用户信息，则将其存入到Spring Security的上下文中，以便于后续的鉴权和授权操作。
 *
 * 需要注意的是，该过滤器中的JwtUtil和RedisCache类需要根据系统的具体情况进行实现和配置。同时，该过滤器还使用了WebUtils类中的renderString方法，用于将响应信息以JSON格式输出到客户端。
 * @author: dingquan
 * @date: 2023/6/12 14:59
 * @param:
 * @param: null
 * @return: null
 **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //说明该接口不需要登录  直接放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);
        //如果获取不到
        if(Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }


}