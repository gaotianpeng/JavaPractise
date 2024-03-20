import com.javapractise.common.utils.Print;
import com.javapractise.daily.designmode.forkjoin.AccumulateTask;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ForkJoinTest {
    @Test
    public void testAccumulateTask() throws ExecutionException, InterruptedException, TimeoutException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        AccumulateTask countTask = new AccumulateTask(1, 100);
        Future<Integer> future = forkJoinPool.submit(countTask);
        Integer sum = future.get(1, TimeUnit.SECONDS);
        Print.tcfo("最终的计算结果：" + sum);
        Assert.assertTrue(sum == 5050);
    }
}
