##患者主索引管理系统(EMPI)
* 患者主索引管理系统:(Enterprise Master Patient Index, EMPI)其主要用途是在一个复杂的医疗体系内，通过唯一的患者标识将多个医疗信息系统有效地关联在一起.

### 采用的技术

#### 后端
* ORM框架: Hibernate + Spring Data JPA
* IOC容器: Spring
* WEB框架: SpringMVC
* Secutiry框架: shiro
* Validator框架: Hibernate Validator
* 定时框架: Quartz
* 报表框架: JasperReport
* 缓存: ehcache
* 数据源: druid
* 日志: slf4j+logback
* JSON: fastjson
* 验证码: jcaptcha

#### 前端
* jeasyui显示界面框架
* jquery框架
* jquery-Validation-Engine验证框架

#### 开发工具
* Eclipse
* JDK1.7+
* Maven3.0+

#### 数据库
* SQL Server 2008 R2

#### 运行环境
* Tomcat7+

#### 管理工具
* Maven3.3+
* Git

###Travis CI自动集成
目前状态: <a href="https://travis-ci.org/ewcmsfree/rmyy"><img src="https://travis-ci.org/ewcmsfree/rmyy.png"/></a>

###其他注意事项

####放大服务内存
修改catalina.sh: JAVA_OPTS="$JAVA_OPTS -server -Xms800m -Xmx800m -XX:PermSize=64m -XX:MaxNewSize=256m -XX:MaxPermSize=128m -Djava.awt.headless=true"

####乱码的问题
* 参数不乱码
在%TOMCAT_HOM%/conf/server.xml其中:
把
<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"/>
替换成
<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/>       
        
 * 报表不乱码
iReport开发的报表乱码，增加如下设置: CATALINA_OPTS='-Djava.awt.headless=true' 
