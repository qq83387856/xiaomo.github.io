package info.xiaomo.gengine.common.utils;

import java.lang.management.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统工具
 *
 * <p>2017-03-31
 */
public final class SysUtil {

	private static final Logger log = LoggerFactory.getLogger(SysUtil.class);
	private static final Runtime RUNTIME = Runtime.getRuntime();
	private static final int MB_NUM = 1024 * 1024;

	private SysUtil() {
	}

	/**
	 * 关闭服务
	 *
	 * @param cls
	 * @param e
	 * @param info
	 * @param obs
	 */
	public static void exit(Class<?> cls, Exception e, String info, Object... obs) {
		log.error(cls.getName(), e);
		log.warn(info, obs);
		System.exit(1);
	}

	/**
	 * 虚拟机可用cpu
	 *
	 * @return 2017年10月12日 下午1:56:46
	 */
	public static int availableProcessors() {
		return RUNTIME.availableProcessors();
	}

	/**
	 * 空闲内存
	 *
	 * @return 2017年10月12日 下午1:59:56
	 */
	public static int freeMemory() {
		return (int) (RUNTIME.freeMemory() / MB_NUM);
	}

	/**
	 * 可用内存
	 *
	 * @return 2017年10月12日 下午2:01:32
	 */
	public static int totalMemory() {
		return (int) (RUNTIME.totalMemory() / MB_NUM);
	}

	/**
	 * 虚拟机能获取的最大内存
	 *
	 * @return 2017年10月12日 下午2:03:08
	 */
	public static int maxMemory() {
		return (int) (RUNTIME.maxMemory() / MB_NUM);
	}

	/**
	 * 类加载信息
	 *
	 * @param splitStr
	 * @return 2017年10月12日 下午3:39:22
	 */
	public static String classLoadInfo(String splitStr) {
		ClassLoadingMXBean bean = ManagementFactory.getClassLoadingMXBean();
		StringBuilder sb = new StringBuilder();
		sb.append("类加载数:   ").append(bean.getLoadedClassCount()).append(splitStr);
		sb.append("类加载总数:   ").append(bean.getTotalLoadedClassCount()).append(splitStr);
		sb.append("类卸载数:   ").append(bean.getUnloadedClassCount()).append(splitStr);
		return sb.toString();
	}

	/**
	 * 类编译信息
	 *
	 * @param splitStr
	 * @return 2017年10月12日 下午3:39:22
	 */
	public static String compilationInfo(String splitStr) {
		CompilationMXBean bean = ManagementFactory.getCompilationMXBean();
		StringBuilder sb = new StringBuilder();
		sb.append("编译器名称:   ").append(bean.getName()).append(splitStr);
		sb.append("编译耗时:   ").append(bean.getTotalCompilationTime()).append(splitStr);
		sb.append("是否支持编译监视:   ").append(bean.isCompilationTimeMonitoringSupported()).append(splitStr);
		return sb.toString();
	}

	/**
	 * 垃圾回收信息
	 *
	 * @param splitStr
	 * @return 2017年10月12日 下午3:39:22
	 */
	public static String collectionInfo(String splitStr) {
		List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < beans.size(); i++) {
			GarbageCollectorMXBean bean = beans.get(i);
			sb.append(i).append("垃圾回收次数:   ").append(bean.getCollectionCount()).append(splitStr);
			sb.append(i).append("垃圾回收累积时间:   ").append(bean.getCollectionTime()).append(splitStr);
		}
		return sb.toString();
	}

	/**
	 * 内存信息
	 *
	 * @param splitStr
	 * @return 2017年10月12日 下午3:39:22
	 */
	public static String memoryInfo(String splitStr) {
		MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
		StringBuilder sb = new StringBuilder();
		sb.append("堆内存使用:   ").append(bean.getHeapMemoryUsage()).append(splitStr);
		sb.append("栈内存使用:   ").append(bean.getNonHeapMemoryUsage()).append(splitStr);
		sb.append("挂起对象数:   ").append(bean.getObjectPendingFinalizationCount()).append(splitStr);
		// 内存池
		List<MemoryPoolMXBean> beans = ManagementFactory.getMemoryPoolMXBeans();
		for (int i = 0; i < beans.size(); i++) {
			MemoryPoolMXBean b = beans.get(i);
			sb.append(i)
					.append("内存池管理器名称:   ")
					.append(Arrays.toString(b.getMemoryManagerNames()))
					.append(splitStr);
			sb.append(i).append("内存池名称:   ").append(b.getName()).append(splitStr);
			sb.append(i).append("内存使用峰值:   ").append(b.getPeakUsage()).append(splitStr);
			sb.append(i).append("内存池类型:   ").append(b.getType()).append(splitStr);
			sb.append(i).append("内存池使用量:   ").append(b.getUsage()).append(splitStr);
		}
		return sb.toString();
	}

	/**
	 * 操作系统信息
	 *
	 * @param spliteStr
	 * @return <p>2017年10月12日 下午5:21:19
	 */
	public static String osInfo(String spliteStr) {
		OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
		StringBuilder sb = new StringBuilder();
		sb.append("操作系统架构:   ").append(bean.getArch()).append(spliteStr);
		sb.append("可使用的cpu数量:   ").append(bean.getAvailableProcessors()).append(spliteStr);
		sb.append("操作系统名称:   ").append(bean.getName()).append(spliteStr);
		sb.append("1分钟cpu消耗平均值:   ").append(bean.getSystemLoadAverage()).append(spliteStr);
		sb.append("操作系统版本:   ").append(bean.getVersion()).append(spliteStr);
		return sb.toString();
	}

	/**
	 * 线程信息
	 *
	 * @param spliteStr
	 * @return <p>2017年10月12日 下午5:21:19
	 */
	public static String threadInfo(String spliteStr) {
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		StringBuilder sb = new StringBuilder();
		ThreadInfo[] threads = bean.dumpAllThreads(false, false);
		for (ThreadInfo thread : threads) {
			sb.append("线程信息:   ").append(thread).append(spliteStr);
		}
		sb.append("活动守护线程数目:   ").append(bean.getDaemonThreadCount()).append(spliteStr);
		sb.append("峰值线程数:   ").append(bean.getPeakThreadCount()).append(spliteStr);
		sb.append("当前线程数:   ").append(bean.getThreadCount()).append(spliteStr);
		sb.append("总启用线程数:   ").append(bean.getTotalStartedThreadCount()).append(spliteStr);
		return sb.toString();
	}

	/**
	 * 虚拟机信息
	 *
	 * @param spliteStr 分隔符
	 * @return 2017年10月12日 下午2:18:01
	 */
	public static String jvmInfo(String spliteStr) {
		StringBuilder sb = new StringBuilder();
		InetAddress addr = null;
		String ip = "";
		try {
			addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
		} catch (UnknownHostException e) {
			log.error("虚拟机地址", e);
		}
		Properties props = System.getProperties();
		Map<String, String> map = System.getenv();
		sb.append("用户名:    ").append(map.get("USERNAME")).append(spliteStr);
		sb.append("计算机名:    ").append(map.get("COMPUTERNAME")).append(spliteStr);
		sb.append("计算机域名:    ").append(map.get("USERDOMAIN")).append(spliteStr);
		sb.append("本地ip地址:    ").append(ip).append(spliteStr);
		sb.append("本地主机名:    ").append(addr.getHostName()).append(spliteStr);
		sb.append("JVM可以使用的总内存:    ").append(totalMemory()).append(spliteStr);
		sb.append("JVM可以使用的剩余内存:    ").append(freeMemory()).append(spliteStr);
		sb.append("JVM可以使用的处理器个数:    ").append(availableProcessors()).append(spliteStr);
		sb.append("Java的运行环境版本：    ").append(props.getProperty("java.version")).append(spliteStr);
		sb.append("Java的运行环境供应商：    ").append(props.getProperty("java.vendor")).append(spliteStr);
		sb.append("Java供应商的URL：    ").append(props.getProperty("java.vendor.url")).append(spliteStr);
		sb.append("Java的安装路径：    ").append(props.getProperty("java.home")).append(spliteStr);
		sb.append("Java的虚拟机规范版本：    ")
				.append(props.getProperty("java.vm.specification.version"))
				.append(spliteStr);
		sb.append("Java的虚拟机规范供应商：    ")
				.append(props.getProperty("java.vm.specification.vendor"))
				.append(spliteStr);
		sb.append("Java的虚拟机规范名称：    ")
				.append(props.getProperty("java.vm.specification.name"))
				.append(spliteStr);
		sb.append("Java的虚拟机实现版本：    ").append(props.getProperty("java.vm.version")).append(spliteStr);
		sb.append("Java的虚拟机实现供应商：    ").append(props.getProperty("java.vm.vendor")).append(spliteStr);
		sb.append("Java的虚拟机实现名称：    ").append(props.getProperty("java.vm.name")).append(spliteStr);
		sb.append("Java运行时环境规范版本：    ").append(props.getProperty("java.specification.version"))
				.append(spliteStr);
		sb.append("Java运行时环境规范供应商：    ").append(props.getProperty("java.specification.vender"))
				.append(spliteStr);
		sb.append("Java运行时环境规范名称：    ").append(props.getProperty("java.specification.name"))
				.append(spliteStr);
		sb.append("Java的类格式版本号：    ").append(props.getProperty("java.class.version")).append(spliteStr);
		sb.append("加载库时搜索的路径列表：    ").append(props.getProperty("java.library.path")).append(spliteStr);
		sb.append("默认的临时文件路径：    ").append(props.getProperty("java.io.tmpdir")).append(spliteStr);
		sb.append("一个或多个扩展目录的路径：    ").append(props.getProperty("java.ext.dirs")).append(spliteStr);
		sb.append("操作系统的名称：    ").append(props.getProperty("os.name")).append(spliteStr);
		sb.append("操作系统的构架：    ").append(props.getProperty("os.arch")).append(spliteStr);
		sb.append("操作系统的版本：    ").append(props.getProperty("os.version")).append(spliteStr);
		sb.append("文件分隔符：    ").append(props.getProperty("file.separator")).append(spliteStr);
		sb.append("路径分隔符：    ").append(props.getProperty("path.separator")).append(spliteStr);
		sb.append("行分隔符：    ").append(props.getProperty("line.separator")).append(spliteStr);
		sb.append("用户的账户名称：    ").append(props.getProperty("user.name")).append(spliteStr);
		sb.append("用户的主目录：    ").append(props.getProperty("user.home")).append(spliteStr);
		sb.append("用户的当前工作目录：    ").append(props.getProperty("user.dir")).append(spliteStr);
		sb.append(classLoadInfo(spliteStr));
		sb.append(compilationInfo(spliteStr));
		sb.append(collectionInfo(spliteStr));
		sb.append(memoryInfo(spliteStr));
		sb.append(osInfo(spliteStr));
		return sb.toString();
	}
}