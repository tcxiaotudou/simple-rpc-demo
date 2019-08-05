package cn.fciasth.calculator.provider;

import cn.fciasth.calculator.provider.service.Calculator;
import cn.fciasth.calculator.provider.service.CalculatorImpl;
import cn.fciasth.calculator.request.CalculatorRpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Provider {

    private static Logger log = LoggerFactory.getLogger(Provider.class);

    private static final int PORT = 9999;

    private Calculator calculator = new CalculatorImpl();

    public static void main(String[] args) throws IOException {
        new Provider().run();
    }

    private void run() throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    // 将请求反序列化
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Object object = objectInputStream.readObject();

                    log.info("request is {}", object);

                    // 调用服务
                    int result = 0;
                    if (object instanceof CalculatorRpcRequest) {
                        CalculatorRpcRequest calculateRpcRequest = (CalculatorRpcRequest) object;
                        if ("add".equals(calculateRpcRequest.getMethod())) {
                            result = calculator.add(calculateRpcRequest.getA(), calculateRpcRequest.getB());
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    }

                    // 返回结果
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(new Integer(result));
                } catch (Exception e) {
                    log.error("fail", e);
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }
}
