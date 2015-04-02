package com.thx.firefightingteam.tools;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thx.firefightingteam.rpc.UserInter;
import com.thx.firefightingteam.rpc.imp.UserInterImp;

import mixedserver.protocol.jsonrpc.client.Client;
import mixedserver.protocol.jsonrpc.mockclient.MockClient;

public class GlobalTools {

	private static Logger logger = LoggerFactory.getLogger(GlobalTools.class);
	// 不理睬本地设置的模拟、演示这些
	static boolean ignoreDemoMode = true;

	// RPC服务器接口地址
	//public static final String SERVER_PATH = "http://127.0.0.1:80/";
	public static final String SERVER_PATH = "http://172.16.40.18:7777/lte/JSON-RPC";
	public static final String SERVER_INTERFACE_PATH = SERVER_PATH;
			//+ "?debug=true";
	// 每页变量
	public static final int numPerPage = 10;

	static final String LOGIN = "userInter";
	static final String SOURCE_WATER="sourceWaterInterface";

	@SuppressWarnings("rawtypes")
	private static HashMap<String, Class> createMockMap() {
		HashMap<String, Class> map = new HashMap<String, Class>();
		map.put(LOGIN, UserInterImp.class);
		map.put(SOURCE_WATER, UserInterImp.class);
		return map;
	}

	public static Client getClient() {
		// 执行Demo模式判断
		if (!ignoreDemoMode) {
		/*	DemoPref_ demoPref = new DemoPref_(null);
			boolean localDemoMode = demoPref.isLocalDemoMode().get();

			if (localDemoMode) {
				// 返回一个本地测试客户端
				MockClient mockClient = MockClient.getClient();
				@SuppressWarnings("rawtypes")
				HashMap<String, Class> map = createMockMap();
				mockClient.setMockClassMap(map);
				return mockClient;
			}*/
			MockClient mockClient = MockClient.getClient();
			@SuppressWarnings("rawtypes")
			HashMap<String, Class> map = createMockMap();
			mockClient.setMockClassMap(map);
			return mockClient;
		}

		Client client = Client.getClient(SERVER_INTERFACE_PATH);
		client.setDencryptMessage(false);
		return client;
	}

	/**
	 * 关闭代理
	 * @param proxy
	 */
	public static void closeProxy(Object proxy) {
		Client client = getClient();
		client.closeProxy(proxy);
	}
	
	
	public static UserInter openLoginProxy(){
		Client client = getClient();
		return client.openProxy(LOGIN, UserInter.class);
	}

	public static boolean isIgnoreDemoMode() {
		return ignoreDemoMode;
	}
}
