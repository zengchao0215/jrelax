package com.jrelax.plugins.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.jrelax.core.web.support.http.HandlerRequest;
import com.jrelax.web.system.entity.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jrelax.core.plugin.IPlugin;
import com.jrelax.core.plugin.annotation.Plugin;
import com.jrelax.web.system.service.LogService;

/**
 * 启动时，自动检测升级目录下文件并执行
 * 
 * @author zenghao
 * 
 */
@Plugin(value = "系统自动升级插件", group = "系统", loadOnStartup = false, order = 2)
public class UpgradePlugin implements IPlugin {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final static String upgradeDirPath = "/upgrade/";
	@Autowired
	private LogService logService;

	@Override
	public boolean init() {
		try {
			final String webpath = this.getClass().getResource("/").toURI().getPath().replace("/WEB-INF/classes/", "");
			File dir = new File(webpath + upgradeDirPath);
			if (dir.exists() && dir.list().length > 0) {
				if (!dir.canRead() || !dir.canExecute() || !dir.canWrite()) {// 判断文件夹目录是否可读
																				// 可写
					logService.info("系统升级", "系统自动升级失败，升级目录访问权限不足！", Log.DEFAULT_USER, new HandlerRequest(Log.DEFAULT_IP, "", ""));
				} else {
					// 1. 执行sql脚本
					final File[] sqlFiles = dir.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(".sql");
						}
					});
					for (File sqlFile : sqlFiles) {
						List<String> sqlList = loadSql(sqlFile);

						for (String sql : sqlList) {
							logService.executeSqlBatch(sql);
							logger.info("SQL: " + sql);
						}
					}
					// 2. 替换文件
					File filesDir = new File(dir.getPath() + "/files");
					if(filesDir.exists()){
						FileUtils.listFilesAndDirs(filesDir, new IOFileFilter() {
							
							@Override
							public boolean accept(File file, String name) {
								return true;
							}
							
							@Override
							public boolean accept(File file) {
								if(!file.isFile()) return false;
								String path = file.getPath().substring(file.getPath().indexOf("files") + 5);
								path = webpath + path;
								File newFile = new File(path);
								if(newFile.exists()){
									try {
										FileUtils.copyFile(file, newFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									logger.info("U："+newFile.getPath());
								}else{
									try {
										newFile.createNewFile();
										FileUtils.copyFile(file, newFile);
									} catch (IOException e) {
										e.printStackTrace();
									}
									logger.info("A："+newFile.getPath());
								}
								return true;
							}
						}, new IOFileFilter() {
							
							@Override
							public boolean accept(File dir, String name) {
								return true;
							}
							
							@Override
							public boolean accept(File dir) {
								if(!dir.isDirectory()) return false;
								String path = dir.getPath().substring(dir.getPath().indexOf("files") + 5);
								path = webpath + path;
								File newDir = new File(path);
								if(!newDir.exists()){
									newDir.mkdirs();
									logger.info("新增："+newDir.getPath());
								}
								return true;
							}
						});
					}
					// 3. 清理文件
					FileUtils.deleteQuietly(dir);
					dir.mkdir();
					logService.info("系统升级", "系统自动升级成功！", Log.DEFAULT_USER, new HandlerRequest(Log.DEFAULT_IP, "", ""));
				}
			}
			logger.info("系统自动升级");
			return true;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	private List<String> loadSql(File sqlFile) {
		List<String> sqlList = new ArrayList<String>();
		if (!sqlFile.exists())
			return sqlList;
		try {
			InputStream sqlFileIn = new FileInputStream(sqlFile);

			StringBuffer sqlSb = new StringBuffer();
			byte[] buff = new byte[1024];
			int byteRead = 0;
			while ((byteRead = sqlFileIn.read(buff)) != -1) {
				sqlSb.append(new String(buff, 0, byteRead));
			}
			// Windows 下换行是 /r/n, Linux 下是 /n
			String[] sqlArr = sqlSb.toString().split("(;//s*//r//n)|(;//s*//n)");
			for (int i = 0; i < sqlArr.length; i++) {
				String sql = sqlArr[i].replaceAll("--.*", "").replaceAll(";", "").trim();
				if (!sql.equals("")) {
					sqlList.add(sql);
				}
			}
			sqlFileIn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sqlList;
	}

	@Override
	public void destroy() {

	}

}
