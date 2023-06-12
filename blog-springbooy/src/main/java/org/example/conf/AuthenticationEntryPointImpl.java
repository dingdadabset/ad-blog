package org.example.conf;

import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @description:这段代码是一个身份验证入口实现类，主要用于在未经过身份验证或者身份验证失败时，响应前端请求并提供错误信息。该实现类实现了AuthenticationEntryPoint接口，并重写了其中的commence方法，实现了对应的身份验证处理逻辑。
 *
 * 具体来说，该实现类会根据不同的身份验证异常类型，返回不同的错误信息。如果是BadCredentialsException异常，则说明登录时用户输入的用户名或密码不正确，返回登录失败的错误信息。如果是InsufficientAuthenticationException异常，则说明用户未经过身份验证，需要重新登录，返回需要登录的错误信息。如果是其他类型的异常，则说明身份验证或授权失败，返回系统错误的错误信息。
 *
 * 在处理完异常后，该实现类会将错误信息以JSON格式响应给前端，使用了WebUtils类中的renderString方法，将响应信息以JSON格式输出到客户端。
 *
 * 需要注意的是，该实现类中的AppHttpCodeEnum枚举类是自定义的错误码枚举，其中包含了各种HTTP请求可能出现的错误码和错误信息。
 * @author: dingquan
 * @date: 2023/6/12 15:33
 * @param:
 * @param: null
 * @return: null
 **/
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        //InsufficientAuthenticationException
        //BadCredentialsException
        ResponseResult result = null;
        if(authException instanceof BadCredentialsException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}