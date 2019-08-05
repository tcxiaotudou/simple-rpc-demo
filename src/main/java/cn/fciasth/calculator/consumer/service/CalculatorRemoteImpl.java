package cn.fciasth.calculator.consumer.service;

import cn.fciasth.calculator.provider.service.Calculator;
import cn.fciasth.calculator.request.CalculatorRpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CalculatorRemoteImpl implements Calculator {

    private static final int PORT = 9999;

    private static Logger log = LoggerFactory.getLogger(CalculatorRemoteImpl.class);

    public int add(int a, int b) {

        List<String> addressList = lookupProviders("Calculator.add");
        String address = chooseTarget(addressList);

        try {
            Socket socket = new Socket(address, PORT);

            //请求序列化
            CalculatorRpcRequest request = generateRequest(a, b);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //发送请求
            objectOutputStream.writeObject(request);
            //响应反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object response = objectInputStream.readObject();
            if (response instanceof Integer){
                return (Integer)response;
            }else {
                throw  new InternalError();
            }

        } catch (Exception e) {
            log.error("fail", e);
            throw new InternalError();
        }
    }

    private CalculatorRpcRequest generateRequest(int a, int b) {
        CalculatorRpcRequest calculateRpcRequest = new CalculatorRpcRequest();
        calculateRpcRequest.setA(a);
        calculateRpcRequest.setB(b);
        calculateRpcRequest.setMethod("add");
        return calculateRpcRequest;
    }

    private String chooseTarget(List<String> providers) {
        if (null == providers || providers.size() == 0) {
            throw new IllegalArgumentException();
        }
        return providers.get(0);
    }

    public static List<String> lookupProviders(String name) {
        List<String> strings = new ArrayList();
        strings.add("127.0.0.1");
        return strings;
    }
}
