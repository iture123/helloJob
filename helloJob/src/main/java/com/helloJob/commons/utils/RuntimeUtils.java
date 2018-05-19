package com.helloJob.commons.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 工具类
 * @author L.cm
 */
public class RuntimeUtils {
    private static final Logger logger = LogManager.getLogger(RuntimeUtils.class);
    
    /**
     * 运行shell
     * @param script
     */
    public static void runShell(String script) {
        Process process = null;
        try {
            String[] cmd = { "sh", script };
            //执行liunx命令
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != process) {
                process.destroy();
            }
        }
    }

}
