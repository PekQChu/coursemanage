package com.global.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shu.model.RunVpsRecord;
import com.shu.xen.api.Vps;

public class CMD {
	public static String projectName = "index";
	public static String userVerifyurl = "http://202.120.119.133:8000/varify.php";
	public static String userVerifyKey = "cpa2014";
	public static String headLeftLogo = "<a class=\"logo\" href=\"#\" style=\"font-size:20px;\"> <img src=\"images/shu.png\" alt=\"layui\" /><!-- <img src=\"layui/images/shu.png\" alt=\"layui\" /> --> </a> ";
	// **xen 相关接口参数**//*
	public static String XEN_API_URL = "http://txxen.hoc.ccshu.net/process.aspx?c=api";
	public static String XEN_API_IDENTITY = "system";// system
	public static String XEN_API_USERNAME = "123456";
	public static String XEN_API_KEY = "8888888834j59080";
	public static double priceCpu = 1;//
	public static double priceMem = 1;// 512mb
	public static double priceStorage = 1;

	public static double priceContainerCpu = 1;// 分/核
	public static double priceContainerMem = 1;// 积分/512MB
	public static double priceContainerGpu = 1;// 积分/个

	public static double objectStorage = 100;// 积分/GB

	/**
	 * 计算 VPS单价
	 * 
	 * @param cpu
	 * @param mem
	 * @param storage
	 * @param isGpu
	 * @return
	 */
	public static double getPrice(int cpu, int mem, int storage, boolean isGpu) {
		return cpu * priceCpu + (mem / 512.0) * priceMem + storage * priceStorage;// 分钟
	}

	/**
	 * 计算容器单价
	 * 
	 * @param cpu
	 * @param mem
	 * @param gpuNum
	 * @return
	 */
	public static double getPriceContainer(int cpu, int mem, int gpuNum) {
		if (cpu == 0)
			cpu = 1;
		if (mem == 0)
			mem = 1024;
		return cpu * priceContainerCpu + (priceContainerMem / 512.0) * priceMem + gpuNum * priceContainerGpu;
	}

	/**
	 * 计算VPS单价
	 * 
	 * @param vps
	 * @return
	 */
	public static double getObjectStorage(int size) {
		return objectStorage * size;
	}

	public static double getDiskStorage(int size) {
		return priceStorage * size;
	}

	public static double getPrice(Vps vps) {
		return vps.getCpu() * priceCpu + (vps.getMaxMem() / 512.0) * priceMem + vps.getDisk() * priceStorage;
	}

	public static double getPrice(RunVpsRecord r) {
		if (r.getType() == 0)
			return r.getCpu() * priceCpu + (r.getMem() / 512.0) * priceMem + r.getStorage() * priceStorage;
		else if (r.getType() == 1)
			return getPriceContainer(r.getCpu(), r.getMem(), r.getGpu());
		else if (r.getType() == 2)
			return getDiskStorage(r.getStorage());
		else
			return getObjectStorage(r.getStorage());
	}
}