package com.dohyeong.preorder;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmailtestApplicationTests {

//    @Autowired
//    private RedisUtil redisUtil;
//
//
//    @Test
//    public void redisTest() throws Exception {
//        //given
//        String email = "test@test.com";
//        String code = "aaa111";
//
//        //when
//        redisUtil.setDataExpire(email, code, 60 * 60L);
//
//        //then
//        Assertions.assertTrue(redisUtil.existData("test@test.com"));
//        Assertions.assertFalse(redisUtil.existData("test1@test.com"));
//        Assertions.assertEquals(redisUtil.getData(email), "aaa111");
//
//    }
}