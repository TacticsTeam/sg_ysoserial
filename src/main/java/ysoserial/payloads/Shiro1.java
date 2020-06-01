package ysoserial.payloads;

import ysoserial.payloads.util.PayloadRunner;

public class Shiro1 implements ObjectPayload<String> {

    @Override
    public String getObject(String command) throws Exception {
        return new String("1");
    }


    public static void main(String[] args) throws Exception {
        PayloadRunner.run(Shiro1.class, args);
    }
}
