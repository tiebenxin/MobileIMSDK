/*
 * Copyright (C) 2020  即时通讯网(52im.net) & Jack Jiang.
 * The MobileIMSDK_X_netty (MobileIMSDK v4.x MINA版) Project. 
 * All rights reserved.
 * 
 * > Github地址：https://github.com/JackJiang2011/MobileIMSDK
 * > 文档地址：  http://www.52im.net/forum-89-1.html
 * > 技术社区：  http://www.52im.net/
 * > 技术交流群：320837163 (http://www.52im.net/topic-qqgroup.html)
 * > 作者公众号：“即时通讯技术圈】”，欢迎关注！
 * > 联系作者：  http://www.52im.net/thread-2792-1-1.html
 *  
 * "即时通讯网(52im.net) - 即时通讯开发者社区!" 推荐开源工程。
 * 
 * ServerLauncherImpl.java at 2020-4-14 23:21:11, code by Jack Jiang.
 */
package net.openmob.mobileimsdk.server.demo;

import java.io.IOException;

import net.openmob.mobileimsdk.server.ServerLauncher;
import net.openmob.mobileimsdk.server.qos.QoS4ReciveDaemonC2S;
import net.openmob.mobileimsdk.server.qos.QoS4SendDaemonS2C;
import net.openmob.mobileimsdk.server.utils.ServerToolKits;
import net.openmob.mobileimsdk.server.utils.ServerToolKits.SenseMode;

/**
 * IM服务的启动主类。
 * <p>
 * <b>友情提示：</b>其实MobileIMSDK的服务端并非只能以main的主类方式独立启
 * 动，你完全可以把它放到诸如java的Web工程里作为子模块运行，不会有任何问题！
 * 
 * @author Jack Jiang
 * @version 1.1
 */
public class ServerLauncherImpl extends ServerLauncher
{
	// 静态类方法：进行一些全局配置设置
	static
	{
		// 设置AppKey（此key目前为保留字段，请忽略之）
		ServerLauncher.appKey = "5418023dfd98c579b6001741";
		
		// 设置MobileIMSDK服务端的网络监听端口
		ServerLauncherImpl.PORT = 7901;

		// 开/关Demog日志的输出
		QoS4SendDaemonS2C.getInstance().setDebugable(true);
		QoS4ReciveDaemonC2S.getInstance().setDebugable(true);
		ServerLauncher.debug = true;
		
		// TODO 与客户端协商一致的心跳敏感模式设置
//		ServerToolKits.setSenseMode(SenseMode.MODE_10S);

		// 关闭与Web端的消息互通桥接器（其实SDK中默认就是false）
		ServerLauncher.bridgeEnabled = false;
		// TODO 跨服桥接器MQ的URI（本参数只在ServerLauncher.bridgeEnabled为true时有意义）
//		BridgeProcessor.IMMQ_URI = "amqp://js:19844713@192.168.31.190";
	}
	
	// 实例构造方法
	public ServerLauncherImpl() throws IOException
	{
		super();
	}
	
    /**
     * 初始化消息处理事件监听者.
     */
	@Override
    protected void initListeners()
    {
    	// ** 设置各种回调事件处理实现类
		this.setServerEventListener(new ServerEventListenerImpl());
		this.setServerMessageQoSEventListener(new MessageQoSEventS2CListnerImpl());
    }
	
    public static void main(String[] args) throws IOException 
    {
    	// 实例化后记得startup哦，单独startup()的目的是让调用者可以延迟决定何时真正启动IM服务
    	new ServerLauncherImpl().startup();
    }
}
