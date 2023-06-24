package org.ljx;

import org.junit.runner.RunWith;
import org.ljx.Mgr.CommentMgr;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class t {

    @Resource
    CommentMgr commentMgr;

    public static void main(String[] args) {
        System.out.println(t.class.getName());

        /**
         * JDK的动态代理 但是必须要有实现类
         */
//        Proxy.newProxyInstance()




    }




}
