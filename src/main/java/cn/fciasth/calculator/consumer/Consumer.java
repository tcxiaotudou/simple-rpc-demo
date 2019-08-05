package cn.fciasth.calculator.consumer;

import cn.fciasth.calculator.consumer.service.CalculatorRemoteImpl;
import cn.fciasth.calculator.provider.service.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
    private static Logger log = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {
        Calculator calculator = new CalculatorRemoteImpl();
        int result = calculator.add(1, 2);
        log.info("result is {}", result);
    }
}
