package com.helloJob.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

public class TestList {

	public static void main(String[] args) {
		List<Integer> ages = Lists.newArrayList(1, 3, 5);
		List<Integer> res = ages.stream().filter(x -> x > 2).collect(Collectors.toList());
		System.out.println(JSON.toJSON(res));
		long beginTime = System.currentTimeMillis();
		boolean isHostReachable = isHostConnectable("192.168.117.12",22);
		System.out.println(System.currentTimeMillis()-beginTime);
		System.out.println(isHostReachable);
	}
	public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
