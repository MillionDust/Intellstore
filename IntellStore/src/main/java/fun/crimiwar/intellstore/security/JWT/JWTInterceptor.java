package fun.crimiwar.intellstore.security.JWT;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import fun.crimiwar.intellstore.util.JWTUtil;
import fun.crimiwar.intellstore.util.SpringBeanFactoryUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/*
JWT过滤器，每次请求验证权限
(加载早，没法@AutoWired，需要从上下文获取)
 */
public class JWTInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String,Object> map = new HashMap<>();
        String jwt = request.getHeader("jwt");
        try {
            //验证令牌
            DecodedJWT decodedJWT = JWTUtil.verifyToken(jwt);
            //验证成功，放行请求====

            //从redis中获取对象信息
            JedisPool jedisPool = SpringBeanFactoryUtil.getBean(JedisPool.class);
            Jedis jedis = jedisPool.getResource();
            String token = decodedJWT.getClaim("token").asString();
            String objJson = jedis.get(token);
//            通过token(jwt生成时产生的UUID)从redis获取对象
//            User user = JSON.parseObject(objJson).toJavaObject(User.class);

            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "无效签名!");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg", "token过期!");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg", "token算法不一致!");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "token无效!!");
        }
        //设置状态
        map.put("state", false);
        //将map转为json
        String json = JSON.toJSONString(map);
        // 相应json数据
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
//        response.sendRedirect("www.baidu.com");重定向
        return false;

    }


}
