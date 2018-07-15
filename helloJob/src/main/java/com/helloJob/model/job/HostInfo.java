package com.helloJob.model.job;
/**
 * 作业执行主机
 * @author iture
 *
 */

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
@TableName("host_info")
@Data
public class HostInfo {
	@TableId(type = IdType.AUTO)
	private Integer id;
	private String protocol;//协议
	private String host;//主机ip
	private Integer port;
	private String username;
	private String passwd;
	private String driverClass;//jdbc的 driver class
	private String jdbcUrl;
	private Long creater;
	private String createTime;
}
