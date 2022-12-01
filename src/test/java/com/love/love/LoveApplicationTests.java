package com.love.love;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class LoveApplicationTests {

    @Test
    void contextLoads() throws ParseException {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String data = ft.format(dNow);
        Date date2 = ft.parse(data);
        System.out.println(date2);


    }

}
