package cn.fciasth.calculator.request;

import java.io.Serializable;

public class CalculatorRpcRequest implements Serializable {
    private static final long serialVersionUID = -3203396834798772320L;

    private String method;

    private int a;

    private int b;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "CalculatorRpcRequest{" +
                "method='" + method + '\'' +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
