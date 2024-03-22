package com.javapractise.daily.concurrency;


import com.javapractise.common.utils.Print;
import com.javapractise.common.utils.ThreadUtils;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureDemo2 {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Test
    public void thenApply() throws Exception {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Print.tcfo("supplyAsync " + Thread.currentThread().getName());
            return "hello";
        }, executorService).thenAccept(s -> {
            try {
                Print.tco("thenApply_test" + s + "world");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Print.tco(Thread.currentThread().getName());

        while (true) {
            if (cf.isDone()) {
                Print.tco("CompletedFuture...is Down");
                break;
            }
        }
    }

    @Test
    public void runAsyncDemo() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Print.tco("run end...");
        });

        future.get(2, TimeUnit.SECONDS);
    }

    @Test
    public void supplyAsyncDemo() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Print.tco("run end ...");
            return System.currentTimeMillis() - start;
        });

        long time = future.get(2, TimeUnit.SECONDS);
        Print.tco("异步执行耗时(s) " + time/1000);
    }

    @Test
    public void whenCompleteDemo() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Print.tco("抛出异常");
            throw new RuntimeException("发生异常");
        });

        future.whenComplete(new BiConsumer<Void, Throwable>() {
            @Override
            public void accept(Void unused, Throwable throwable) {
                Print.tco("执行完成！");
            }
        });

        future.exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                Print.tco("执行失败！" + throwable.getMessage());
                return null;
            }
        });

        future.get();
    }

    @Test
    public void handleDemo() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Print.tco("抛出异常");
            throw new RuntimeException("发生异常");
        });
        
        future.handle(new BiFunction<Void, Throwable, Void>() {
            @Override
            public Void apply(Void unused, Throwable throwable) {
                if (throwable == null) {
                    Print.tcfo("没有发生异常！");
                } else {
                    Print.tcfo("sorry, 发生了异常！");
                }
                return null;
            }
        });

        future.get();
    }

    @Test
    public void threadPoolDemo() throws Exception {
        ThreadPoolExecutor pool = ThreadUtils.getMixedTargetThreadPool();
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            Print.tco("run begin ...");
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Print.tco("run end ...");
            return System.currentTimeMillis() - start;
        }, pool);

        long time = future.get(2, TimeUnit.SECONDS);
        Print.tco("异步执行耗时(s) " + time/1000);
    }

    @Test
    public void thenApplyDemo() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long firstStep = 10L + 10L;
                Print.tco("firstStep outcome is " + firstStep);
                return firstStep;
            }
        }).thenApplyAsync(new Function<Long, Long>() {
            @Override
            public Long apply(Long firstStepOutCome) {
                long secondStep = firstStepOutCome * 2;
                Print.tco("secondStep outcome is " + secondStep);
                return secondStep;
            }
        });

        long result = future.get();
        Print.tco(" future is " + future);
        Print.tco(" outcome is " + result);
    }

    @Test
    public void thenComposeDemo() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long firstStep = 10L + 10L;
                Print.tco("firstStep outcome is " + firstStep);

                return firstStep;
            }
        }).thenCompose(new Function<Long, CompletionStage<Long>>() {
            @Override
            public CompletionStage<Long> apply(Long firstStepOutcome) {
                return CompletableFuture.supplyAsync(new Supplier<Long>() {
                    @Override
                    public Long get() {
                        long secondStep = firstStepOutcome * 2;
                        Print.tco("secondStep outcome is " + secondStep);
                        return secondStep;
                    }
                });
            }
        });

        long result = future.get();
        Print.tco(" outcome is " + result);
    }

    @Test
    public void thenCombineDemo() throws Exception {
        CompletableFuture<Integer> future1 =
                CompletableFuture.supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        Integer firstStep = 10 + 10;
                        Print.tco("firstStep outcome is " + firstStep);
                        return firstStep;
                    }
                });
        CompletableFuture<Integer> future2 =
                CompletableFuture.supplyAsync(new Supplier<Integer>() {

                    @Override
                    public Integer get() {
                        Integer secondStep = 10 + 10;
                        Print.tco("secondStep outcome is " + secondStep);
                        return secondStep;
                    }
                });
        CompletableFuture<Integer> future3 = future1.thenCombine(future2,
                new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer step1OutCome, Integer step2OutCome) {
                        return step1OutCome * step2OutCome;
                    }
                });

        Integer result = future3.get();
        Print.tco(" outcome is " + result);
    }

    @Test
    public void allOfDemo() throws Exception {
        CompletableFuture<Void> future1 =
                CompletableFuture.runAsync(() -> Print.tco("模拟异步任务1"));

        CompletableFuture<Void> future2 =
                CompletableFuture.runAsync(() -> Print.tco("模拟异步任务2"));
        CompletableFuture<Void> future3 =
                CompletableFuture.runAsync(() -> Print.tco("模拟异步任务3"));
        CompletableFuture<Void> future4 =
                CompletableFuture.runAsync(() -> Print.tco("模拟异步任务4"));

        CompletableFuture<Void> all =
                CompletableFuture.allOf(future1, future2, future3, future4);
        all.join();
    }

    @Test
    public void applyToEitherDemo() throws Exception {
        CompletableFuture<Integer> future1 =
                CompletableFuture.supplyAsync(new Supplier<Integer>() {

                    @Override
                    public Integer get() {
                        Integer fitstStep = 10 + 10;
                        Print.tco("firstStep outcome is " + fitstStep);
                        return fitstStep;
                    }
                });

        CompletableFuture<Integer> future2 =
                CompletableFuture.supplyAsync(new Supplier<Integer>() {

                    @Override
                    public Integer get() {
                        Integer secondStep = 100 + 100;
                        Print.tco("secondStep outcome is " + secondStep);
                        return secondStep;
                    }
                });

        CompletableFuture<Integer> future3 = future1.applyToEither(future2,
                new Function<Integer, Integer>() {

                    @Override
                    public Integer apply(Integer eitherOutcome) {
                        return eitherOutcome;
                    }
                });

        Integer result = future3.get();
        Print.tco(" outcome is " + result);
    }
}
