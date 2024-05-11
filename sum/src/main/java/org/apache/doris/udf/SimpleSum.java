package org.apache.doris.udf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class SimpleSum {
    Logger log = Logger.getLogger("SimpleSum");

    /*required*/
    // 需要一个内部类来存储数据
    public static class State {
        // 如果需要，可以使用一些变量
        public int sum = 0;
    }

    /*required*/
    public State create() {
        // 如果需要，这里可以进行一些初始化工作
        return new State();
    }

    /*required*/
    public void destroy(State state) {
        // 如果需要，这里可以进行一些销毁工作
    }

    /*Not Required*/
    public void reset(State state) {
        // 如果你希望这个 UDAF 函数能够与窗口函数一起工作
        // 必须实现这个方法，每计算完一个窗口帧后，它将被重置为初始状态
        state.sum = 0;
    }

    /*required*/
    // 第一个参数是 State，然后是你输入的其他类型
    public void add(State state, Integer val) throws Exception {
        // 在有输入数据时进行更新工作
        if (val != null) {
            state.sum += val;
        }
    }

    /*required*/
    public void serialize(State state, DataOutputStream out)  {
        // 将一些数据序列化到缓冲区中
        try {
            out.writeInt(state.sum);
        } catch (Exception e) {
            // 不要抛出异常
            log.info(e.getMessage());
        }
    }

    /*required*/
    public void deserialize(State state, DataInputStream in)  {
        // 在你放入数据之前，从缓冲区获取数据进行反序列化
        int val = 0;
        try {
            val = in.readInt();
        } catch (Exception e) {
            // 不要抛出异常
            log.info(e.getMessage());
        }
        state.sum = val;
    }

    /*required*/
    public void merge(State state, State rhs) throws Exception {
        // 合并state中的数据
        state.sum += rhs.sum;
    }

    /*required*/
    // 返回您定义的类型
    public Integer getValue(State state) throws Exception {
        // 返回最终结果
        return state.sum;
    }
}