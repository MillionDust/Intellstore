package fun.crimiwar.intellstore;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
//import com.jayway.jsonpath.Configuration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import fun.crimiwar.intellstore.domain.User;
import fun.crimiwar.intellstore.util.AuthDecodeUtil;
import fun.crimiwar.intellstore.util.JWTUtil;
import fun.crimiwar.intellstore.util.MailUtil;
import fun.crimiwar.intellstore.util.RedisUtil;
import org.apache.kafka.clients.producer.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

@SpringBootTest
class IntellStoreApplicationTests {

    @Autowired
    private JedisPool jedisPool;


    @Test
    void contextLoads() {

                Properties props = new Properties();
                props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.116.130:9092");
                props.put(ProducerConfig.ACKS_CONFIG, "all");
                props.put(ProducerConfig.RETRIES_CONFIG, 1);
                props.put("batch.size", 16384);
                props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
                props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
                props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
                props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
                Producer<String, String> producer = new KafkaProducer<String, String>(props);
                for (int i = 1; i <= 100; i++) {
                    // 构造消息体,主要是在这里使用使用了一个回调函数new CallBack()
                    producer.send(new ProducerRecord<>("test", "test-" + i, "test-" + i), new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            if (e == null) {
                                System.out.println(recordMetadata.partition() + "-" + recordMetadata.offset());
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                producer.close();
            }


    @Test
    public void JWTTest(){
        Jedis jedis = jedisPool.getResource();
        HashMap pl = new HashMap();
        String token = UUID.randomUUID().toString().replaceAll("-","");
        String id = "1cc3gb";
        String name = "Bob";
//        jedis.set(token, JSON.toJSONString(new User(id,name)));
        pl.put("token",token);
        pl.put("id",id);
        pl.put("name",name);
        String jwt = JWTUtil.createJWT(pl);
        DecodedJWT decodedJWT = JWTUtil.verifyToken(jwt);
        System.out.println(jwt);
    }

    @Autowired
    MailUtil mailUtil;

    @Test
    public void mail(){
        try {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        ClassLoader classLoader = IntellStoreApplication.class.getClassLoader();
        configuration.setClassLoaderForTemplateLoading(classLoader,"templates");
        Template template = configuration.getTemplate("mail.ftl");

        StringWriter mail = new StringWriter();
        User user = new User("123","BOb");
        template.process(user,mail);
        mailUtil.send("3635573716@qq.com","1147422477@qq.com","title",mail.toString(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (TemplateException e){
            e.printStackTrace();
        }
    }

    @Autowired
    AuthDecodeUtil authDecodeUtil;

    @Test
    public  void auth(){

        authDecodeUtil.DecodeAuth(100);
    }


}
