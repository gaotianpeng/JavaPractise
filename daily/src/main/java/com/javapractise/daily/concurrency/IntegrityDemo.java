package com.javapractise.daily.concurrency;

import com.javapractise.common.utils.Print;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class IntegrityDemo {
    public String rpc1() {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Print.tcfo("模拟RPC调用：服务器 server 1");
        return "sth. from server 1";
    }

    public String rpc2() {
        //睡眠400ms,模拟执行耗时
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Print.tcfo("模拟RPC调用：服务器 server 2");
        return "sth. from server 2";
    }

    @Test
    public void rpcDemo() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return rpc1();
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> rpc2());
        CompletableFuture<String> future3 = future1.thenCombine(future2,
                (out1, out2) ->
                {
                    return out1 + " & " + out2;
                });
        String result = future3.get();
        Print.tco("客户端合并最终的结果：" + result);
    }
}
