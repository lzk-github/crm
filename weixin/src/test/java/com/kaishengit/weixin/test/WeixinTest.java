package com.kaishengit.weixin.test;

import com.kaishengit.weixin.util.WeixinUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-weixin.xml")
public class WeixinTest {
    @Autowired
    private WeixinUtil weixinUtil;

//    @Test
    public void test() {
        System.out.println(weixinUtil.getAccessToken(""));
    }
//    @Test
    public void testCreateDept() {
        weixinUtil.createDept(1007,1,"总经理办公室");
        weixinUtil.createDept(1008,1,"销售部");
        weixinUtil.createDept(1014,1,"呵呵部");
        weixinUtil.createDept(1018,1,"售后服务部");
    }
//    @Test
    public void testDeleteDept() {
        weixinUtil.deleteByDeptId(20);
    }
}
