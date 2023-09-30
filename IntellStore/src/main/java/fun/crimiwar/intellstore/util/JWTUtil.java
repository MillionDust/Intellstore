package fun.crimiwar.intellstore.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

//    过期时间0.5h
    private final static long EXPIRATION = 1000*60*30l;

//    密钥
    private final static String SECRET_KEY = "afe1e377a9d9abb51f12e4adf07b2419";


    /*
    获取JWT(加密)
     */
    public static String createJWT(Map<String,String> PayLoad){
//        设置过期时间
        Date date = new Date(System.currentTimeMillis()+EXPIRATION);
//        设置头部
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

//        创建TOKEN
        JWTCreator.Builder builder = JWT.create();

//        创建PayLoad
        PayLoad.forEach(builder::withClaim);

//        加密完成
        return builder.withHeader(header)
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static DecodedJWT verifyToken(String token) {

        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);

    }

}
