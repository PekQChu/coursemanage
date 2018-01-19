package com.txcourse.service;

import java.net.URL;
import java.util.Map;
import java.util.Set;

import com.xensource.xenapi.APIVersion;
import com.xensource.xenapi.Connection;
import com.xensource.xenapi.Session;
import com.xensource.xenapi.Types;
import com.xensource.xenapi.VIF;
import com.xensource.xenapi.VM;

public class XenAPIService {
	private static Session session;
//	private static Connection connection = connectToServer("192.168.130.171", "root", "!QAZ2wsx");
	private static Connection connection = connectToServer("10.1.112.123", "root", "NJI(bhu8");
	
	public static Connection connectToServer(String serverIp, String username, String password) {
		System.err.println("houst ip:" + serverIp);
		System.err.println("连接中...");
		Connection c = null;
		try {
			c = new Connection(new URL("http://" + serverIp));
			System.err.println("连接" + serverIp + "成功！");
			System.err.println("登录中...");
	        session = Session.loginWithPassword(c, username, password, APIVersion.latest().toString());
	        System.err.println("登录" + serverIp + "成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("登录" + serverIp + "失败！");
			e.printStackTrace();
		}
        return c;
	}
	
	public static void showVMIp(String vmNameLabel) throws Exception {
		if (vmNameLabel == null) {
			//获取所有机器的所有网卡
			Map<VM, VM.Record> vmMap = VM.getAllRecords(connection);
			for (VM vm : vmMap.keySet()) { 
				if (!vm.getIsATemplate(connection) && !vm.getIsControlDomain(connection)) {
					System.out.println(vm.getNameLabel(connection));
					Set<VIF> vifs = vm.getVIFs(connection);
					for (VIF v : vifs) {
						System.out.println("ip gateway:" + v.getIpv4Gateway(connection));
						System.out.println("ip allowed:" + v.getIpv4Allowed(connection));
						System.out.println("ip:" + v.getIpv4Addresses(connection));
					}
				}
			}
		} else {
			Set<VM> vms = VM.getByNameLabel(connection, vmNameLabel);
			for (VM vm : vms) { 
				if (!vm.getIsATemplate(connection) && !vm.getIsControlDomain(connection)) {
					System.out.println(vm.getNameLabel(connection));
					Set<VIF> vifs = vm.getVIFs(connection);
					for (VIF v : vifs) {
						System.out.println("ip gateway:" + v.getIpv4Gateway(connection));
						System.out.println("ip allowed:" + v.getIpv4Allowed(connection));
						System.out.println("ip:" + v.getIpv4Addresses(connection));
					}
				}
			}
		}
		disconnectToServer();
	}
	
	public static void showAllTemplates() throws Exception {
		VM vm = (VM) VM.getByNameLabel(connection, "XenAPITest_Windows_7_64").toArray()[0];
		Map<String, String> ipMap = vm.getGuestMetrics(connection).getNetworks(connection);
		for (String s : ipMap.keySet()) {
			System.out.println(s + "---" + ipMap.get(s));
		}
		Set<VIF> vifs = vm.getVIFs(connection);
		for (VIF v : vifs) {
			System.out.println("1");
			if (v.getDevice(connection).equals("0")) {
				v.configureIpv4(connection, Types.VifIpv4ConfigurationMode.STATIC, "10.1.139.51/24", "10.1.139.254");
////				v.unplug(connection);
////				v.destroy(connection);
			} else {
//				System.out.println("destroy v:" + v.getDevice(connection));
//				v.destroy(connection);
			}
		}
//		Network network = null;
//		Set<Network> networks = Network.getByNameLabel(connection, "Pool-wide network associated with eth2");
//		System.out.println("networks.size = " + networks.size());
//		for (Network n : networks) {
//			network = n;
//			System.out.println(n.getNameLabel(connection));
//		}
//		VIF.Record newvifrecord = new VIF.Record();
//
//        // These three parameters are used in the command line VIF creation
//        newvifrecord.VM = vm;
//        newvifrecord.network = network;
//        newvifrecord.device = "9";
//        newvifrecord.MTU = 1500L;
//        newvifrecord.lockingMode = Types.VifLockingMode.NETWORK_DEFAULT;
//
//        VIF v =  VIF.create(connection, newvifrecord);
//        v.plug(connection);
//        v.configureIpv4(connection, Types.VifIpv4ConfigurationMode.STATIC, "10.1.139.51/24", "10.1.139.254");
		disconnectToServer();
	}
	
	public static void createFromTemplate() throws Exception {
//		VM template = VM.getByUuid(connection, "c47bad79-7d43-41c6-1769-4b33d7f23676");
//		VM newVm = template.createClone(connection, "cloned VPS");
//		newVm.provision(connection);
//		newVm.start(connection, false, false);
//		VIF newVmVif = (VIF) newVm.getVIFs(connection).toArray()[0];
//		newVmVif.configureIpv4(connection, Types.VifIpv4ConfigurationMode.STATIC, "192.168.6.221/24", "192.168.6.1");
		
		
		
//		VM vm = null;
//		try {
//			vm = VM.getByUuid(connection, "9a065877-7bfe-16eb-7ffc-5d71a9eb5903");
//		} catch (BadServerResponse e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (XenAPIException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (XmlRpcException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		VIF vif = null;
//		try {
//			vif = (VIF) vm.getVIFs(connection).toArray()[0];
////			System.out.println(vif.getMetrics(connection).getOtherConfig(connection).toString());
//		} catch (BadServerResponse e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (XenAPIException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (XmlRpcException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
////			System.out.println(vif.getAllowedOperations(connection).toString());
//			vif.configureIpv4(connection, Types.VifIpv4ConfigurationMode.STATIC, "192.168.6.221/24", "192.168.6.1");
//		} catch (BadServerResponse e) {
//			// TODO Auto-generated catch block
//			System.out.println("BadServerResponse");
//			e.printStackTrace();
//		} catch (XenAPIException e) {
//			// TODO Auto-generated catch block
//			System.out.println("XenAPIException");
//			e.printStackTrace();
//		} catch (XmlRpcException e) {
//			// TODO Auto-generated catch block
//			System.out.println("XmlRpcException");
//			e.printStackTrace();
//		}
		disconnectToServer();
	}
	
	public static void createUbuntuFromTemplate() throws Exception {
//		Set<Pool> pools = Pool.getAll(connection);
//        Pool pool = (Pool)pools.toArray()[0];
//        SR storage = pool.getDefaultSR(connection);
        
		VM template = VM.getByUuid(connection, "51817bc5-c6a6-e752-bbd1-b964847fafe1");
		System.out.println(template.getRecord(connection).toString());
		VM template1 = VM.getByUuid(connection, "0ff5cab4-fb1a-fa4d-1caa-be7be543089b");
		System.out.println(template1.getRecord(connection).toString());
		
//		showIps("9a065877-7bfe-16eb-7ffc-5d71a9eb5903");
//		VM newVm = template.createClone(connection, "cloned VPS win10 1");
//		Map<String, String> otherConfig = newVm.getOtherConfig(connection);
//        otherConfig.put("hostname", "testhost");
//        otherConfig.put("password", "testhost");
////        disks = disks.replace("sr=\"\"", "sr=\"" + storage.getUuid(connection) + "\"");
////        otherConfig.put("disks", disks);
//        newVm.setOtherConfig(connection, otherConfig);
//		newVm.provision(connection);
//		newVm.start(connection, false, false);
        
        
//		VIF newVmVif = (VIF) template.getVIFs(connection).toArray()[0];
	
//		newVmVif.configureIpv4(connection, Types.VifIpv4ConfigurationMode.STATIC, "192.168.6.121/24", "192.168.6.1");

//		VM vm = VM.getByUuid(connection, "3db6ca04-3455-19a7-fa11-0ca098b6d7e2");
//		vm.getGuestMetrics(connection).
		//vif.configureIpv4(connection, Types.VifIpv4ConfigurationMode.STATIC, "192.168.6.221/24", "192.168.6.1");
		
		disconnectToServer();
	}
	
	public static void createVpsFromXenOwnTMPL() throws Exception {
//		Set<VM> vms = VM.getByNameLabel(connection, "Ubuntu Xenial Xerus 16.04");
//		for (VM v : vms) {
//			System.out.println(v.getUuid(connection));
//		}
		VM template = VM.getByUuid(connection, "585b47d5-0671-66bd-e57e-8576367235c1");
		VM newVm = template.createClone(connection, "cloned VPS ubuntu1604 1");
		newVm.provision(connection);
		newVm.start(connection, false, false);
		disconnectToServer();
	}
	
	public static void showIps(String vmUuid) throws Exception {
		VM vm = VM.getByUuid(connection, vmUuid);
		Map<String, String> ipMap = vm.getGuestMetrics(connection).getNetworks(connection);
		for (String s : ipMap.keySet()) {
			System.out.println(s + "---" + ipMap.get(s));
		}
	}
	
	public static void disconnectToServer() {
        try {
        	System.err.println("断开连接中...");
			Session.logout(connection);
			System.err.println("断开成功！");
		} catch (Exception e) {
			System.err.println("断开失败！");
			e.printStackTrace();
		}
    }
}
