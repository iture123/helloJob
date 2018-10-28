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
		List<Integer> ages = Lists.newArrayList(0,1, 2, 3,4);
		System.out.println(ages.subList(0, 2));
		System.out.println(ages.subList(8, ages.size()));
	}

}
