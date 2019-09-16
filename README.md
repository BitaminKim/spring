# SSM整合

### 环境搭建

1. 新建Maven项目，不选择任何模版下一步，定义组名等信息

2. 选择项目结构Project Structure选项，选择Facets新建Web项，Deployment Descriptors中编辑Path为"系统路径/项目名/src/main/webapp/WEB-INF/web.xml"（eclipse则右键项目选中properties，找到Project Facets，把Dynamic Web Module'动态web模块'去除勾选后Apply应用，然后重新勾选上后再Apply应用一次后下方出现Futher Configuration available，选中改选项后在窗口中勾选上Generate web.xml deployment descriptor后选中ok即可。

3. (可选)配置生成war包，选择项目结构Project Structure选项，选择Artifacts新建Web Application Archive->For '项目名:Web exploded'后选择ok即可（如果要自行指定根目录则选Empty然后在Output Layout选项卡中点加号选中Directory Content后选择项目根目录后ok即可）

4. 配置[Tomcat](http://tomcat.apache.org/) ，从官网下载（Mac需要对解压后的目录bin进行chmod 755 *.sh 赋予权限）后在Preferences->Build,Execution,Deployment->Application Servers 中添加Tomcat 选中Tomcat路径后按OK.接着在Idea右上角找到Edit Configurations 添加TomcatServer 即可

5. 引入SSM框架的Maven Pom。（如Maven较慢可以在Maven配置文件settings.xml中mirror更换镜像源）

   ```xml
   <!--注释原有Maven仓库的mirror标签-->
   <mirror>
   	<id>alimaven</id>
     <mirrorOf>central</mirrorOf>
     <name>aliyun maven</name>
     <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
   </mirror>
   ```

6. 更改Maven工程使用的JDK版本和编译器版本与目前项目使用的JDK版本一致

   ```xml
   <profile>
     <id>jdk-1.12</id>
     <activation>
       <activeByDefault>true</activeByDefault>
       <jdk>1.12</jdk>
     </activation>
     <properties>
       <maven.compiler.source>1.12</maven.compiler.source>
       <maven.compiler.target>1.12</maven.compiler.target>
       <maven.compiler.compilerVersion>1.12</maven.compiler.compilerVersion>
     </properties>
   </profile>
   ```

7. 声明版本号和项目编码，以后统一在这里进行管理

   ```xml
   <properties>
     <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
     <spring.version>5.1.9.RELEASE</spring.version>
     <mybatis.version>3.5.2</mybatis.version>
     <slf4j.version>1.7.28</slf4j.version>
   </properties>
   ```

8. 添加[Spring Web MVC控制器](https://mvnrepository.com/artifact/org.springframework/spring-webmvc) [Spring JDBC](https://mvnrepository.com/artifact/org.springframework/spring-jdbc) [Spring Test](https://mvnrepository.com/artifact/org.springframework/spring-test) [Spring Aspects面向切面](https://mvnrepository.com/artifact/org.springframework/spring-aspects) [MyBatis](https://mvnrepository.com/artifact/org.mybatis/mybatis) [MyBatis-Spring适配器](https://mvnrepository.com/artifact/org.mybatis/mybatis-spring) [MyBatis-Generator代码生成器](https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core) [MyBatis-PageHelper](https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper) [Mysql 数据库驱动](https://mvnrepository.com/artifact/mysql/mysql-connector-java)(Mysql数据库驱动8.0版本使用com.mysql.cj.jdbc.Driver) [数据库连接池c3p0](https://mvnrepository.com/artifact/com.mchange/c3p0) 

   老版本Spring使用log4j1的情况 [slf4j-api日志接口](https://mvnrepository.com/artifact/org.slf4j/slf4j-api) [slf4j-log4j12接口驱动](https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12) [log4j](https://mvnrepository.com/artifact/log4j/log4j) 

   新版本Spring5.0以上使用slf4j-log4j2的情况:

   |             Application(SLF4J-API To LOG4J-API)              |
   | :----------------------------------------------------------: |
   | [SLF4J API (slf4j-api.jar)](https://mvnrepository.com/artifact/org.slf4j/slf4j-api) |
   |             LOG4J Bridge (log4j-slf4j-impl.jar)              |
   | [Log4j2.x API (log4j-api-2.x.jar)](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api) |
   | [Log4j2 Implementation (log4j-core-2.x.jar)](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core) |

   使用log4j2-api的情况要兼容回slf4j的话:

   |             Application(LOG4J-API To SLF4J-API)              |
   | :----------------------------------------------------------: |
   | [Log4j2.x API (log4j2-api-2.x.jar)](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api) |
   | [SLF4J Bridge (log4j-to-slf4j-2.x.jar)](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-to-slf4j) |
   | [SLF4J API (slf4j-api.jar)](https://mvnrepository.com/artifact/org.slf4j/slf4j-api) |
   | [SLF4J Implementation (log4j-1.x.jar)](https://mvnrepository.com/artifact/log4j/log4j) |

   

   推荐目前直接使用log4j-api规范  [log4j-api](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api) [log4j-core](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core) [log4j-web](https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-web) [异步输出日志](https://mvnrepository.com/artifact/com.lmax/disruptor)

   和其他需要的依赖（mvnrepository.com中找）[alibaba fastjson](https://mvnrepository.com/artifact/com.alibaba/fastjson) [jackson-core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core) [jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind) [JSR303-HibernateVaildator](https://mvnrepository.com/artifact/org.hibernate/hibernate-validator) [servlet-api](https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api ) [jstl](https://mvnrepository.com/artifact/jstl/jstl) [Junit](https://mvnrepository.com/artifact/junit/junit) 到Pom文件中

9. 配置web.xml 引入SSM框架

   1. 配置Spring配置文件路径，默认不写会读取/WEB-INF/applicationContext.xml这个路径，param-value可以使用*通配符导入多个文件

      ```xml
      <context-param>
      	<param-name>contextConfigLocation</param-name>
      	<param-value>classpath:applictionContext.xml</param-value>
      </context-param>
      ```

   2. 配置ContextLoaderListener容器加载监听器(SpringMVC监听器)

      ```xml
      <listener>
      	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
      </listener>
      ```

   3. 配置SpringMvc前端控制器SpringDispatcherServlet

      ```xml
      <servlet>
        <servlet-name>SpringDispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param><!--指定SpringMvc配置文件位置(可选)，如果不指定则可以创建一个以"${servlet-name}-servlet.xml"的文件在web.xml目录下，Spring会自动获取到该文件。否则就以制定的路径为准。-->
          <param-name>contextConfigLocation</param-name>
          <param-value>location</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
      </servlet>
      <servlet-mapping><!--配置路径-->
        <servlet-name>SpringDispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
      </servlet-mapping>
      ```

   4. 配置字符编码过滤器

      ```xml
      <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param><!--配置过滤编码-->
          <param-name>encoding</param-name>
          <param-value>utf-8</param-value>
        </init-param>
        <init-param><!--配置是否过滤请求-->
          <param-name>forceRequestEncoding</param-name>
          <param-value>true</param-value>
        </init-param>
        <init-param><!--配置是否过滤响应-->
          <param-name>forceResponseEncoding</param-name>
          <param-value>true</param-value>
        </init-param>
      </filter>
      <filter-mapping><!--配置过滤路径-->
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
      </filter-mapping>
      ```

   5. 配置日志框架log4j

      ```xml
      <!-- 老版本5.0以下Spring使用log4j1的情况 -->
      <context-param>
        <param-name>log4jConfigLocation</param-name>
        <param-value>classpath:log4j.properties</param-value>
      </context-param>
      <listener>
        <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
      </listener>
      <!-- 新版本5.0以上Spring使用log4j2的情况 -->
      <context-param><!-- 不指定则默认着src下的log4j2.xml和log4j2.properties和yaml文件或者json，2.0~2.4不支持properties -->
        <param-name>log4jConfigLocation</param-name>
        <param-value>classpath:log4j2.xml</param-value>
      </context-param>
      <listener>
        <listener-class>org.apache.logging.log4j.web.Log4jServletContextListener</listener-class>
      </listener>
      
      <filter>
        <filter-name>log4jServletFilter</filter-name>
        <filter-class>org.apache.logging.log4j.web.Log4jServletFilter</filter-class>
      </filter>
      <filter-mapping>
        <filter-name>log4jServletFilter</filter-name>
        <url-pattern>/*</url-pattern>
        <dispatcher>REQUEST</dispatcher>
        <dispatcher>FORWARD</dispatcher>
        <dispatcher>INCLUDE</dispatcher>
        <dispatcher>ERROR</dispatcher>
      </filter-mapping>
      ```
      
      
      
   6. 配置Rest风格转换器(可选)

      ```xml
      <filter><!--配置过滤请求方式达到RestFul风格 将POST请求转换为DELETE(删除)或PUT(更新)请求 只需在参数中增加_method既可让Spring转换为对应请求方式接收 主要是为了解决form表单只支持get post的问题，让表单提交的时候添加_method参数来达到模拟其他请求-->
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
      </filter>
      <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
      </filter-mapping>	
      ```

10. 配置applictionContext.xml，在resources下创建SpringConfig的xml文件'applictionContext.xml' (配置业务逻辑)

   1. 配置Spring容器扫描过滤(扫描除了Controller注解以外的所有组件)

      ```xml
      <context:component-scan base-package="ink.bitamin">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
      </context:component-scan>
      ```

   2. 配置数据源

      ```xml
      <!--配置properties外部配置文件引入-->
      <context:property-placeholder location="classpath:dataSourceConfig.properties" />
      <!--配置数据源-->
       <bean id="pooledDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" >
         <property name="jdbcUrl" value="${dataSourceConfig.jdbcUrl}" />
         <property name="driverClass" value="${dataSourceConfig.driverClass}" />
         <property name="user" value="${dataSourceConfig.user}" />
         <property name="password" value="${dataSourceConfig.password}" />
      </bean>
      
      ```

      在resources目录创建dataSourceConfig.properties文件

      ```properties
      dataSourceConfig.jdbcUrl=jdbc:mysql://localhost:3306/spring
      dataSourceConfig.driverClass=com.mysql.jdbc.Driver
      dataSourceConfig.user=root
      dataSourceConfig.password=root
      
      ```

   3. 配置整合Mybatis到Spring

      ```xml
       <bean id="SqlSessionFactoryBean"  class="org.mybatis.spring.SqlSessionFactoryBean">
         <!--配置mybatis配置文件 如果不想用外部配置文件也可以在bean中用property去配置-->
         <property name="configLocation" value="classpath:mybatisConfig.xml" />
         <!--配置数据源信息-->
         <property name="dataSource" ref="pooledDataSource" />
         <!--配置mapper映射文件 映射到resources/mappers目录下的所有文件-->
         <property name="mapperLocations" value="classpath:mapper/*" />
      </bean>
      <!--配置Mybatis扫描器，把mybatis接口的实现加入到IOC容器中-->
      <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
         <!--扫描dao包下的所有文件到IOC-->
        <property name="basePackage" value="ink.bitamin.dao"/>
      </bean>
      
      ```

   4. 事务控制

      ```xml
      <!--配置事务控制-->
      <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--配置要被切入控制的数据源信息-->
        <property name="dataSource" ref="pooledDataSource"/>
      </bean>
      
      <!--配置事务增强如何切入 如果配置事务控制的bean的id为transactionManager则transaction-manager可以不用写，因为transaction-manager的默认值就是transactionManager-->
      <tx:advice id="txAdvice" transaction-manager="dataSourceTransactionManager">
        <tx:attributes>
          <!--所有方法都是事务方法-->
          <tx:method name="*"/>
          <!--以方法名为get开始的所有方法作为只读事务，如果该方法出现写操作则事务失败-->
          <tx:method name="get*" read-only="true"/>
        </tx:attributes>
      </tx:advice>
      <!--配置基于注解的事务配置或者xml方式的事务配置，正常情况均使用xml配置方式更为灵活-->
      <aop:config>
        <!--配置切入点表达式 execution(任意返回值 包名下任意包..任意类*任意方法(任意形参))-->
        <aop:pointcut id="txPoint" expression="execution(* ink.bitamin.service..*(..))"/>
        <!--配置事务增强 指向id为txAdvice-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPoint"/>
      </aop:config>
      
      ```
      
      ####tx:method有关设置：
      
      | 属性              | 是否需要？ | 默认值   | 描述                                                         |
      | :---------------- | :--------- | :------- | :----------------------------------------------------------- |
      | `name`            | 是         |          | 与事务属性关联的方法名。通配符（\*）可以用来指定一批关联到相同的事务属性的方法。 如：`'get*'`、`'handle*'`、`'on*Event'`等等。 |
      | `propagation`     | 不         | REQUIRED | 事务传播行为                                                 |
      | `isolation`       | 不         | DEFAULT  | 事务隔离级别                                                 |
      | `timeout`         | 不         | -1       | 事务超时的时间（以秒为单位）                                 |
      | `read-only`       | 不         | false    | 事务是否只读？（典型地，对于只执行查询的事务你会将该属性设为true，如果出现了更新、插入或是删除语句时只读事务就会失败） |
      | `rollback-for`    | 不         |          | 将被触发进行回滚的 `Exception(s)`；以逗号分开。 如：`'com.foo.MyBusinessException,ServletException'` |
      | `no-rollback-for` | 不         |          | 不 被触发进行回滚的 `Exception(s)`；以逗号分开。 如：`'com.foo.MyBusinessException,ServletException'` |

11. 配置dispatcherServlet.xml，在resources下创建SpringConfig的xml文件 (页面跳转逻辑)

    1. 配置扫描组件过滤 只扫描Controller注解

       ```xml
       <context:component-scan base-package="ink.bitamin" use-default-filters="false">
         <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
       </context:component-scan>
       
       ```

    2. 配置视图解析器 prefix为视图所在目录 suffix为.jsp后缀的文件

       ```xml
       <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
         <property name="prefix" value="/WEB-INF/view/"></property>
         <property name="suffix" value=".jsp"></property>
       </bean>
       
       ```

    3. 配置SpringMVC不能处理的请求交给Web容器和高级功能（标准配置）

       ```xml
       <mvc:default-servlet-handler/><!--配置SpringMVC不能处理的请求给默认容器-->
       <mvc:annotation-driven/><!--配置注解高级功能驱动-->
       <mvc:annotation-driven><!--此处配置可以使用注解直接让实现类注入bean-->
         <!--配置过滤实体空参数转换JSON，实体参数为null时过滤-->
         <mvc:message-converters register-defaults="true">
           <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
             <property name="objectMapper">
               <!--配置过滤的实现类-->
               <bean class="ink.bitamin.base.CustomerObjectMapper"></bean>
             </property>
             <property name="supportedMediaTypes">
       				<list>
       					<value>text/html;charset=UTF-8</value>
       					<value>application/json;charset=UTF-8</value>
       				</list>
       			</property>
           </bean>
         </mvc:message-converters>
       </mvc:annotation-driven>
       <!-- 配置AOP注解自动扫描 -->
       <aop:aspectj-autoproxy expose-proxy="true" />
       
    ```
       
       ```java
       import com.fasterxml.jackson.core.JsonGenerator;
       import com.fasterxml.jackson.core.JsonProcessingException;
       import com.fasterxml.jackson.databind.JsonSerializer;
       import com.fasterxml.jackson.databind.ObjectMapper;
       import com.fasterxml.jackson.databind.SerializerProvider;
       import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
       
       import java.io.IOException;
       public class CustomerObjectMapper extends ObjectMapper {
           {
               DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
               sp.setNullValueSerializer(new NullSerializer());
               this.setSerializerProvider(sp);
           }
           public class NullSerializer extends JsonSerializer<Object> {
               public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException,JsonProcessingException {
                   jgen.writeString("");
               }
           }
       }
       
       //关于实体类转JSON过滤空参数的时候，也可以用注解注入容器中即可
       @Configuration
       public class CustomConfig {
           @Bean
           public ObjectMapper objectMapper() {
               ObjectMapper objectMapper = new ObjectMapper();
               objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                   @Override
                   public void serialize(Object paramT, JsonGenerator paramJsonGenerator,
                                         SerializerProvider paramSerializerProvider) throws IOException {
                       //设置返回null转为 空字符串""
                       paramJsonGenerator.writeString("");
                   }
               });
               return objectMapper;
           }
       }
       
       ```

    

12. 配置mybatisConfig.xml，在resources下创建SpringConfig的xml文件 (配置数据映射)

    1. 从[Mybatis官网](http://www.mybatis.org/mybatis-3/zh/index.html)中的[入门](http://www.mybatis.org/mybatis-3/zh/getting-started.html)中获取到xml的配置文件

       ```xml
       <?xml version="1.0" encoding="UTF-8" ?>
       <!DOCTYPE configuration
               PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
               "http://mybatis.org/dtd/mybatis-3-config.dtd">
       <configuration>
         <settings><!-- 配置驼峰 -->
           <setting name="mapUnderscoreToCamelCase" value="true" />
         </settings>
         <typeAliases><!-- 配置别名 -->
           <package name="ink.bitamin.entity"/>
         </typeAliases>
         <plugins><!-- 配置Mybatis分页插件，可到pageHelper官网获取详细配置 https://pagehelper.github.io -->
           <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
         </plugins>
       </configuration>
       
       ```

13. 配置日志框架log4j.properties(也可以配置XML),在项目根目录下创建日志规则配置的properties文件

    ```properties
    # 日志框架分3大组件 
    # Loggers记录器(日志框架的基本配置,日志级别)
    #  Loggers日志级别依次优先级分为[DEBUG<INFO<WARN<ERROR<FATAL]
    #  声明的时候声明输出级别和附加器名称(可自定义附加器名称,下方配置需跟该名称一致)
    # Appenders附加器(日志的目标->'文件'，'控制台',日志输出方式) 
    #  Appenders输出目的分为[
    #		ConsoleAppender(控制台),
    #		FileAppender(文件),
    #		DailyRollingFileAppender(每天产生一个日志文件)
    #		RollingFileAppender(按照文件大小到达指定尺寸的时候产生一个新的日志文件)
    #	]
    # Layouts布局器(日志具体格式,日志输出的格式) 使用指定的占位符进行格式处理
    
    #1.x版本配置，
    log4j.rootLogger = debug,stdout,fout
    
    log4j.appender.stdout = org.apache.log4j.ConsoleAppender
    
    log4j.appender.stdout.Target = System.out
    log4j.appender.stdout.Threshold = debug
    log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
    log4j.appender.stdout.layout.ConversionPattern = %d [%-5p]%l %m%n
    
    log4j.appender.fout = org.apache.log4j.DailyRollingFileAppender
    log4j.appender.fout.File = log/log.log
    
    log4j.appender.fout.DatePattern = yyyy-MM-dd'.log'
    log4j.appender.fout.Encoding = UTF-8
    log4j.appender.fout.Append = true
    log4j.appender.fout.ImmediateFlush= true
    log4j.appender.fout.Threshold = DEBUG
    log4j.appender.fout.layout = org.apache.log4j.PatternLayout
    log4j.appender.fout.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}[%-5p]%l %m%n
    
    
    # 2.x版本配置
    
    status = warn
    name = MyApp
     
    appender.console.type = Console
    appender.console.name = consoleLog
    appender.console.filter.threshold.type = ThresholdFilter
    appender.console.filter.threshold.level = debug
    appender.console.layout.type = PatternLayout
    appender.console.layout.pattern = %m%n
    appender.console.target = System_out
     
    appender.rolling.type = File
    appender.rolling.name = fileLog
    appender.rolling.filter.threshold.type = ThresholdFilter
    appender.rolling.filter.threshold.level = error
    appender.rolling.layout.type = PatternLayout
    appender.rolling.layout.pattern = %d-%m%n
    appender.rolling.append = true
    appender.rolling.fileName = e:\\log.log
     
    rootLogger.level = debug
    rootLogger.appenderRef.consolelogdemo.ref = consoleLog
    rootLogger.appenderRef.filelogdemo.ref = fileLog
    ```

    ```xml
    <!-- 旧版本1.x的xml配置 -->
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
    <log4j:configuration xmlns:log4j='http://jakarta.apache.org/log4j/' >
    	<properties>
        <demo.loggingLevel>INFO</demo.loggingLevel>
      </properties>
      
      <appender name="myConsole" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
          <param name="ConversionPattern" value="[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%p] - %m - %l%n" />
        </layout>
    
        <!--过滤器设置输出的级别-->   
        <filter class="org.apache.log4j.varia.LevelRangeFilter">
          <param name="levelMin" value="debug" />
          <param name="levelMax" value="warn" />
          <param name="AcceptOnMatch" value="true" />
        </filter>
      </appender>
    
    
      <appender name="myFile" class="org.apache.log4j.RollingFileAppender">
        <param name="File" value="log/log.log" /><!-- 设置日志输出文件名和路径 -->
        <!-- 设置是否在重新启动服务时，在原有日志的基础添加新日志 -->
        <param name="Append" value="true" />
        <param name="MaxBackupIndex" value="10" />
        <layout class="org.apache.log4j.PatternLayout">
          <param name="ConversionPattern" value="%p (%c:%L)- %m%n" />
        </layout>
      </appender>
    
      <!-- 推荐主要配置这个类型 -->
      <appender name="activexAppender" class="org.apache.log4j.DailyRollingFileAppender">
        <param name="File" value="log/activex.log" />
        <param name="DatePattern" value="'.'yyyy-MM-dd'.log'" />
        <layout class="org.apache.log4j.PatternLayout">
          <param name="ConversionPattern" value="[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%p] - %m - %l%n" />
        </layout>
      </appender>
      
      <!-- 区分模块可以使用这种配置 -->
      <appender name="errorAppender" class="org.apache.log4j.DailyRollingFileAppender">
        <param name="File" value="log/common-error.log" />
        <param name="Append" value="true" /> 
        <param name="Encoding" value="UTF-8" />  
        <param name="Threshold" value="error" />
        <layout class="org.apache.log4j.PatternLayout">
          <param name="ConversionPattern" value="[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%p] - %m - %l%n" />
        </layout>
      </appender>
      
      <appender name="moduleAppender" class="org.apache.log4j.DailyRollingFileAppender">
        <param name="File" value="log/module.log" />   
        <param name="Append" value="true" /> 
        <param name="Encoding" value="UTF-8" />
        <layout class="org.apache.log4j.PatternLayout">
          <param name="ConversionPattern" value="[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%p] - %m - %l%n" />
        </layout>
      </appender>
    
      <!-- 指定logger的设置，additivity指示是否遵循缺省的继承机制-->
      <logger name="ink.bitamin" additivity="false">
        <priority value ="info"/>
        <appender-ref ref="activexAppender" />
      </logger>
      
      <logger name="ink.bitamin.controller" additivity="false">
        <priority value ="info"/>
        <appender-ref ref="moduleAppender" />  
        <appender-ref ref="errorAppender" />
      </logger>
    
      <!-- 根logger的设置-->
      <root>
        <level value="${demo.loggingLevel}"/>
        <appender-ref ref="myConsole"/>
        <appender-ref ref="myFile"/>
      </root>
    
    </log4j:configuration>
    
    <!-- 新版本2.x的xml配置 官方默认文件名log4j2.xml-->
    <!-- ${sys:catalina.home} 指向tomcat目录 -->
    <?xml version="1.0" encoding="UTF-8"?>
    <Configuration status="warn">
      <properties>
        <property name="LOG_HOME">logs</property>
        <property name="FILE_NAME">log</property>    
        <property name="LOGGER_LEVEL">info</property>
      </properties>
      
    	<appenders>
    		<Console name="consoleLog" target="SYSTEM_OUT">
    			<ThresholdFilter level="debug"/>
    			<PatternLayout pattern="%m%n" />
    		</Console>
    		
    		<File name="fileLog" fileName="${sys:catalina.home}/${LOG_HOME}/${FILE_NAME}.log" append="true">
    			<ThresholdFilter level="${LOGGER_LEVEL}" />
    			<PatternLayout pattern="%d-%m%n" />
    		</File>
        
        <RollingFile name="rollingFileLog" fileName="${sys:catalina.home}/${LOG_HOME}/${FILE_NAME}.log" append="true" filePattern="${sys:catalina.home}/${LOG_HOME}/$${date:yyyy-MM}/${FILE_NAME}-%d{yyyy-MM-dd}-%i.log.gz"
                immediateFlush="true">
    			<ThresholdFilter level="${LOGGER_LEVEL}" />
    			<PatternLayout pattern="%date{yyyy-MM-dd HH:mm:ss.SSS} %level [%thread][%file:%line] - %msg%n" />
          <Policies>
            <TimeBasedTriggeringPolicy /><!-- interval="2 hour" 指定多长时间滚动一次-->
            <SizeBasedTriggeringPolicy size="10 MB" /><!-- 指定文件多大滚动一次-->
          </Policies>
          <DefaultRolloverStrategy max="20" /><!-- 设置同名文件i最大值（文件夹下最大文件数），超过则覆盖-->
    		</RollingFile>
        
        <!-- 去除了1.x版本的DailyRollingFileAppender，通过更改PatternLayout格式时间来控制按时间分文件和配置TimeBasedTriggeringPolicy控制精度，此处为24小时的意思 -->
        <RollingFile name="dailyRollingFileLog" fileName="${sys:catalina.home}/${LOG_HOME}/${FILE_NAME}.log" append="true" filePattern="${sys:catalina.home}/${LOG_HOME}/$${date:yyyy-MM}/${FILE_NAME}-%d{yyyy-MM-dd}-%i.log.gz"
                immediateFlush="true">
    			<ThresholdFilter level="${LOGGER_LEVEL}" />
    			<PatternLayout pattern="%-d{yyyy-MM-dd HH:mm:ss} [%thread] %m%n"/>
        		<Policies>
        			<TimeBasedTriggeringPolicy modulate="true" interval="24 hour"/>
        		</Policies>
          <DefaultRolloverStrategy max="20"/>
    		</RollingFile>
        
    	</appenders>
    	     
    	<loggers>
    		<root level="${LOGGER_LEVEL}">
    			<appender-ref ref="consoleLog"></appender-ref>
    			<appender-ref ref="fileLog"></appender-ref>
    		</root>
    	</loggers>
    
    ```

    标准的按照文件大小和时间分割的xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration status="error">
    	<!-- 先定义所有的appender -->
    	<appenders>
    		<!-- 这个输出控制台的配置 -->
    		<Console name="Console" target="SYSTEM_OUT">
    			<!-- 控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch） -->
    			<ThresholdFilter level="debug" onMatch="ACCEPT"
    				onMismatch="DENY" />
    			<!-- 这个都知道是输出日志的格式 -->
    			<PatternLayout
    				pattern="[ %-20class{1} ] %msg%n" />
    		</Console>
    		<!-- 这个会打印出所有的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面，作为存档 -->
    		<RollingFile name="RollingFile-info" fileName="log4j2/info/info.log"
    			filePattern="log4j2/info/$${date:yyyy-MM}/%d{MM-dd-yyyy}-%i.log">
    			<!-- pattern = "[ 日志级别 | 类名 | 方法名 | 行数 | 线程名 | 区分客户端 | 时间 ] - 日志信息" -->
    			<PatternLayout
    				pattern="[ %level{length=1} | %-20class{1} | %-15M | %-2L | %-2t | %X{5} | %d{yyyy-MM-dd HH:mm:ss} ] %msg%n" />
    			<Policies>
    				<TimeBasedTriggeringPolicy interval="24" />			<!--多长时间滚动一次 -->
    				<SizeBasedTriggeringPolicy size="1 MB" />		<!-- 一个日志文件的最大大小 -->
    			</Policies>
    			<DefaultRolloverStrategy max="20" />   <!--文件夹下最多的文件个数 -->
    		</RollingFile>
    		<RollingFile name="RollingFile-debug" fileName="log4j2/debug/debug.log"
    			filePattern="log4j2/debug/$${date:yyyy-MM}/%d{MM-dd-yyyy}-%i.log">
    			<!-- pattern = "[ 日志级别 | 类名 | 方法名 | 行数 | 线程名 | 区分客户端 | 时间 ] - 日志信息" -->
    			<PatternLayout
    				pattern="[ %level{length=1} | %-20class{1} | %-15M | %-2L | %-2t | %X{5} | %d{yyyy-MM-dd HH:mm:ss} ] %msg%n" />
    			<Policies>
    				<TimeBasedTriggeringPolicy interval="24" />			<!--多长时间滚动一次 -->
    				<SizeBasedTriggeringPolicy size="1024 KB" />		<!-- 一个日志文件的最大大小 -->
    			</Policies>
    			<DefaultRolloverStrategy max="20" />   <!--文件夹下最多的文件个数 -->
    		</RollingFile>
    		<RollingFile name="RollingFile-error" fileName="log4j2/error/error.log"
    			filePattern="log4j2/error/$${date:yyyy-MM}/%d{MM-dd-yyyy}-%i.log">
    			<!-- pattern = "[ 日志级别 | 类名 | 方法名 | 行数 | 线程名 | 区分客户端 | 时间 ] - 日志信息" -->
    			<PatternLayout
    				pattern="[ %level{length=1} | %-20class{1} | %-15M | %-2L | %-2t | %X{5} | %d{yyyy-MM-dd HH:mm:ss} ] %msg%n" />
    			<Policies>
    				<TimeBasedTriggeringPolicy interval="24" />			<!--多长时间滚动一次 -->
    				<SizeBasedTriggeringPolicy size="1024 KB" />		<!-- 一个日志文件的最大大小 -->
    			</Policies>
    			<DefaultRolloverStrategy max="20" />   <!--文件夹下最多的文件个数 -->
    		</RollingFile>
        
        <!-- 仅保存距今24小时的日志压缩文件 -->
        <RollingRandomAccessFile name="msgAppender" immediateFlush="true"  
                                 fileName="${MSG_LOG_HOME}/msg.log"  
                                 filePattern="${MSG_LOG_HOME}/backup/msg.%d{yyyyMMddHH}.zip">  
    
          <Filters>  
            <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>  
          </Filters>  
    
          <PatternLayout pattern="%m%n" />  
          <Policies>  
            <TimeBasedTriggeringPolicy interval="1" modulate="true"/>  
          </Policies>    
    
          <DefaultRolloverStrategy max="24">  
            <Delete basePath="${MSG_LOG_HOME}" maxDepth="2">  
              <IfFileName glob="*/msg.*.zip" />  
              <IfLastModified age="24H" />  
            </Delete>  
          </DefaultRolloverStrategy>  
        </RollingRandomAccessFile>  
    	</appenders>
    	<!-- 然后定义logger，只有定义了logger并引入的appender，appender才会生效 -->
    	<loggers>
    		<!-- 此处name里面的为对应的包名，包名必须和里面的类的package相同 -->
    		<logger name="ink.bitamin.info" level="debug">
    			<!-- ref的值是对应使用的appenders的值 -->
    			<appender-ref ref="RollingFile-info"/>
    		</logger>
    		<logger name="ink.bitamin.debug" level="debug" >
    			<appender-ref ref="RollingFile-debug"/>
    		</logger>
    		<AsyncLogger name="ink.bitamin.error" level="debug">
    			<appender-ref ref="RollingFile-error"/>
    		</AsyncLogger>
    		<!-- 建立一个默认的root的logger -->
    		<root level="trace">
    			<appender-ref ref="Console" />
    		</root>
    	</loggers>
    </configuration>
    ```

    

14. 配置mybatisGeneratorCore.xml，在项目根目录下创建代码生成配置的xml文件 (配置生成代码)

    1. 从Mybatis官网的[Generator工具页面](http://www.mybatis.org/generator/configreference/xmlconfig.html)获取到Mybatis-generator-core的xml配置文件并修改

       ##### generator.xml使用需要修改的点:

       classPathEntry的location属性，该属性指定的是本机的MySQL驱动包的绝对路径地址。
       jdbcConnection的connectionURL，userId，password属性，该属性用于指定MySQL数据库的链接地址，用户名以及密码。
       javaModelGenerator的targetPackage，targetProject属性，该属性用于指定生成的实体类的所在包名以及代码生成位置。
       sqlMapGenerator的targetPackage，targetProject属性，该属性用于指定生成的*.xml文件的所在包名以及代码生成位置。
       javaClientGenerator的targetPackage，targetProject属性，该属性用于指定生成的mapper文件的所在包名以及代码生成位置。
       table的tableName，domainObjectName属性，该属性用于指定需要生成的数据表名称以及生成后的实体类名称。其中table标签可以使用多次，一次对应一张表。

       ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       <!DOCTYPE generatorConfiguration
         PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
         "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
       
       <generatorConfiguration>
         
         <!-- 配置mysql 驱动jar包路径.用了绝对路径 用非Java代码的方式时需要手动指定驱动 
         <classPathEntry location="/Users/bitamin/.m2/repository/mysql/mysql-connector-java/5.1.34/mysql-connector-java-5.1.34.jar" />
         -->
         <context id="DB2Tables" targetRuntime="MyBatis3">
           
           <!-- 配置生成代码的时候去除注释 -->
           <commentGenerator>
             <property name="suppressAllComments" value="true" />
           </commentGenerator>
           
           <!-- 配置连接数据库的信息 xml中&需要使用&amp;代替 -->
           <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                           connectionURL="jdbc:mysql://localhost:3306/spring?useSSL=false&amp;characterEncoding=utf-8&amp;serverTimezone=UTC"
                           userId="root"
                           password="root">
           </jdbcConnection>
       
           <javaTypeResolver >
             <property name="forceBigDecimals" value="false" />
           </javaTypeResolver>
       	
           <!-- 配置数据库对应的Bean实体生成位置 目标包名 目标项目 -->
           <javaModelGenerator targetPackage="ink.bitamin.entity" targetProject="./src/main/java">
             <property name="enableSubPackages" value="true" />
             <property name="trimStrings" value="true" />
           </javaModelGenerator>
       
           <!-- 配置SQL映射文件生成位置 目标包名 目标项目 -->
           <sqlMapGenerator targetPackage="mapper"  targetProject="./src/main/resources">
             <property name="enableSubPackages" value="true" />
           </sqlMapGenerator>
       
           <!-- 配置DAO接口生成位置 目标包名 目标项目 -->
            <javaClientGenerator type="XMLMAPPER" targetPackage="ink.bitamin.dao"  targetProject="./src/main/java">
              <property name="enableSubPackages" value="true" />
           </javaClientGenerator>
       
           <!-- 定义每一个table表的生成策略 -->
           <table tableName="account" domainObjectName="Account" />
           <table tableName="department" domainObjectName="Department" />
       
         </context>
       </generatorConfiguration>
       
       ```

    2. 有4种方式可以生成代码 ，最常用的有eclipse，IDEA-Maven，Java，Maven命令行方式

       Java方式（[官网](http://www.mybatis.org/generator/running/runningWithJava.html)可找到该代码）编写完毕后运行即可生成

       ```Java
       package ink.bitamin.test;
       
       import org.mybatis.generator.api.MyBatisGenerator;
       import org.mybatis.generator.config.Configuration;
       import org.mybatis.generator.config.xml.ConfigurationParser;
       import org.mybatis.generator.internal.DefaultShellCallback;
       
       import java.io.File;
       import java.util.ArrayList;
       import java.util.List;
       
       public class MBGTest {
           public static void main(String[] args) throws Exception {
               List<String> warnings = new ArrayList<String>();
               boolean overwrite = true;
               File configFile = new File("generatorConfig.xml");
               ConfigurationParser cp = new ConfigurationParser(warnings);
               Configuration config = cp.parseConfiguration(configFile);
               DefaultShellCallback callback = new DefaultShellCallback(overwrite);
               MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
               myBatisGenerator.generate(null);
           }
       }
       ```

       IDEA-Maven方式

       ```xml
       <!-- pom中配置生成器插件和导入数据库驱动依赖 -->
       
       <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.34</version>
       </dependency>
       
       <build>
         <plugins>
           <plugin>
             <groupId>org.mybatis.generator</groupId>
             <artifactId>mybatis-generator-maven-plugin</artifactId>
             <version>1.3.5</version>
             <configuration>
               <verbose>true</verbose>
               <overwrite>true</overwrite>
             </configuration>
           </plugin>
         </plugins>
       </build>
       
       <!-- 使用IDEA右侧的maven运行mybatis-generator-maven-plugin插件：工程名->Plugins->mybatis-generator->mybatis-generator:generate->Run Maven Build -->
       ```

       命令行方式(下载Mybatis生成代码的Jar包执行)

       ```bash
       java -jar mybatis-generator-core-1.3.2.jar -configfile generator.xml -overwrite
       ```

    3. 

### Mybatis业务修改

#### 生成代码优化

##### Entity

1. 创建一个抽象的BasicDO作为Entity的基类，

   ```java
   //@JsonInclude(JsonInclude.Include.NON_NULL) 使用该jackson注解的话可以过滤null参数
   public abstract class BasicDO implements Serializable {
       public BasicDO() {}
   }
   ```

2. 创建一个抽象的BasicExampleDO作为EntityExample的基类

   ```java
   public abstract class BasicExampleDO implements Serializable {
       public BasicExampleDO() {}
   }
   ```

3. 让生成的entity，entityExample对象继承该基类，使BasicMapper基类的形参通用，Mybatis实现接口的时候返回的对象为该基类的子类时不会有类型错误问题

##### Dao

1. 创建一个BasicMapper作为DAO的基类，编写通用的CURD操作（可从生成的代码获取，改变形参为BasicDO和BasicExampleDO基类）

   ```java
   long countByExample(BasicExampleDO example);
   int deleteByExample(BasicExampleDO example);
   int deleteByPrimaryKey(Long id);
   int insert(BasicDO record);
   int insertSelective(BasicDO record);
   List<BasicDO> selectByExample(BasicExampleDO example);
   BasicDO selectByPrimaryKey(Long id);
   int updateByExampleSelective(@Param("record") BasicDO record, @Param("example") BasicExampleDO example);
   int updateByExample(@Param("record") BasicDO record, @Param("example") BasicExampleDO example);
   int updateByPrimaryKeySelective(BasicDO record);
   int updateByPrimaryKey(BasicDO record);
   ```

2. 让其他生成的DAO代码继承该基类，并删除生成代码的重复方法，如AccountMapper增加两个新方法

   ```java
   public interface AccountMapper extends BasicMapper {
       //可以使用脚本编写接口实现，使用xml语法 （不推荐 不便于维护）
       //@Select("<script>select <if test="distinct"> distinct </if> <include refid="With_Dept_Column_List" /> from account a left join department d on a.`id`=d.`id` <if test="_parameter != null"> <include refid="Example_Where_Clause" /> </if> <if test="orderByClause != null"> order by ${orderByClause} </if></script>") 
       List<AccountVO> selectByExampleWithDepartment(AccountExample example);
       //可以使用注解方式来编写接口实现，不用使用xml配置
     	//直接写SQL
     	// @Select("select a.id, a.dept_id, a.username, a.password, a.status, a.gmt_create, a.gmt_modified, d.id d_id, d.dept_name from account a left join department d on a.`id`=d.`id` where a.`id` = #{id}")
     
     	//使用JAVA代码配置方法返回SQL 类似于直接写impl的实现类
     	//对应的也有增@InsertProvider、改@UpdateProvider、删@DeleteProvider
     	/*
     	@SelectProvider(type = AccountMapperProvider.class, method = "selectByPrimaryKeyWithDepartment")
     	class AccountMapperProvider { 
       		//方法一，直接返回sql字符串
           public String selectByPrimaryKeyWithDepartment(Long id) {  
               String sql = "select a.id, a.dept_id, a.username, a.password, a.status, a.gmt_create, a.gmt_modified, d.id d_id, d.dept_name from account a left join department d on a.`id`=d.`id`";  
               if(id != null){  
                   sql += " where id = #{id}";  
               }  
               return sql;  
           }  
           //方法二，返回一个SQL对象的toString
           public String selectByPrimaryKeyWithDepartment(Long id) {
               return new SQL(){{      
                   SELECT("a.id, a.dept_id, a.username, a.password, a.status, a.gmt_create, a.gmt_modified");
                   SELECT("d.id d_id, d.dept_name");
                   FROM("account a");
                   LEFT_OUTER_JOIN("department d on a.`id`=d.`id`"); 
                   if(id != null){  
                   		WHERE("id = #{id}"); 
                   }    
               }}.toString();
           }  
       }  
       */
       AccountVO selectByPrimaryKeyWithDepartment(Long id);
     
   }
   ```

   

3. 

##### VO

1. 如果返回给前端的对象与数据库对应的DO对象需求不一致，使用VO对象作为返回视图封装

```java
public class AccountVO extends Account {
  	//@Pattern(regexp="()", message="这是一个配合JSR303的数据校验，控制器接收参数使用@Valid对接收形参对象注解 并且可以给入一个BindingResult对象使用判断校验结果 bindingResult.hasErrors() 如果错误可以bindingResult.getFieldErrors()获取错误信息集合 集合对象getField()获取校验失败字段，集合对象getDefaultMessage()获取错误信息")
    private DepartmentVO departmentVO;
    public DepartmentVO getDepartmentVO() {
        return departmentVO;
    }
    public void setDepartmentVO(DepartmentVO departmentVO) {
        this.departmentVO = departmentVO;
    }
}
```

##### Mapper.xml

```xml
<!-- 外联查询时可以使用extends添加返回参数(可选)，也可以直接重新声明resultMap -->
<resultMap id="BaseResultMap" type="ink.bitamin.entity.Account">
  <id column="id" jdbcType="BIGINT" property="id" />
  <result column="dept_id" jdbcType="BIGINT" property="deptId" />
  <result column="username" jdbcType="VARCHAR" property="username" />
  <result column="password" jdbcType="VARCHAR" property="password" />
  <result column="status" jdbcType="TINYINT" property="status" />
  <result column="gmt_create" jdbcType="TIMESTAMP" property="gmtCreate" />
  <result column="gmt_modified" jdbcType="TIMESTAMP" property="gmtModified" />
</resultMap>
<resultMap id="WithDeptResultMap" type="ink.bitamin.entity.vo.AccountVO" extends="BaseResultMap" >
  <!-- 声明返回数据类型，使用vo的AccountVo接收，并且聚合DepartmentVO对象数据 -->
  <association property="departmentVO" javaType="ink.bitamin.entity.vo.DepartmentVO">
    <!-- 返回结果有相同的字段'id' 此处使用结果中的d_id别名识别使其对应到DepartmentVO对象的id属性 -->
    <!-- property代表Java对应对象，column代表数据库返回的字段，jdbcType代表数据库字段类型-->
    <id column="d_id" jdbcType="BIGINT" property="id" />
    <result column="dept_name" jdbcType="VARCHAR" property="deptName" />
  </association>
</resultMap>
<!-- 外联查询时重新声明返回字段 -->
<sql id="With_Dept_Column_List">
  a.id, a.dept_id, a.username, a.password, a.status, a.gmt_create, a.gmt_modified, d.id d_id, d.dept_name
</sql>
<!-- 新增外链查询的接口实现 返回的结果用继承的WithDeptResultMap接收（因为外联数据在BaseResultMap中没有）-->
<select id="selectByExampleWithDepartment" parameterType="ink.bitamin.entity.AccountExample" resultMap="WithDeptResultMap">
  select
  <if test="distinct">
    distinct
  </if>
  <include refid="With_Dept_Column_List" />
  from account a left join department d on a.`id`=d.`id`
  <if test="_parameter != null">
    <include refid="Example_Where_Clause" />
  </if>
  <if test="orderByClause != null">
    order by ${orderByClause}
  </if>
</select>
<select id="selectByPrimaryKeyWithDepartment" parameterType="java.lang.Long" resultMap="WithDeptResultMap">
  select
  <include refid="With_Dept_Column_List" />
  from account a left join department d on a.`id`=d.`id`
  where a.`id` = #{id,jdbcType=BIGINT}
</select>

```

### 前端控制器

1. 配置一个返回通用数据结构的封装对象

   ```java
   
   package ink.bitamin.entity;
   
   import java.util.HashMap;
   import java.util.Map;
   
   public class MessageVO {
   
       private int code;
       private String msg;
       private Map<String, Object> data = new HashMap<String, Object>();
   
       public static MessageVO success(){
           MessageVO messageVO = new MessageVO();
           messageVO.setCode(200);
           messageVO.setMsg("处理成功!");
           return messageVO;
       }
       public static MessageVO success(int code, String msg){
           MessageVO messageVO = new MessageVO();
           messageVO.setCode(code);
           messageVO.setMsg(msg);
           return messageVO;
       }
       public static MessageVO fail(){
           MessageVO messageVO = new MessageVO();
           messageVO.setCode(0);
           messageVO.setMsg("请求失败!");
           return messageVO;
       }
       public static MessageVO fail(int code, String msg){
           MessageVO messageVO = new MessageVO();
           messageVO.setCode(code);
           messageVO.setMsg(msg);
           return messageVO;
       }
       //状态码后携带数据的方法
       public MessageVO add(String key, Object value) {
           this.getData().put(key,value);
           return this;
       }
   
       public int getCode() {
           return code;
       }
   
       public void setCode(int code) {
           this.code = code;
       }
   
       public String getMsg() {
           return msg;
       }
   
       public void setMsg(String msg) {
           this.msg = msg;
       }
   
       public Map<String, Object> getData() {
           return data;
       }
   
       public void setData(Map<String, Object> data) {
           this.data = data;
       }
   }
   ```

2. 编写AOP切面切入Controller层打印Request请求和Response响应信息和总耗时

   ```java
   package ink.bitamin.aspects;
   
   import org.apache.logging.log4j.LogManager;
   import org.apache.logging.log4j.Logger;
   import org.aspectj.lang.JoinPoint;
   import org.aspectj.lang.ProceedingJoinPoint;
   import org.aspectj.lang.annotation.*;
   import org.springframework.stereotype.Component;
   import org.springframework.web.context.request.RequestContextHolder;
   import org.springframework.web.context.request.ServletRequestAttributes;
   
   import javax.servlet.http.HttpServletRequest;
   import java.util.Arrays;
   
   @Aspect
   @Component
   public class WebLogAspect {
       private final static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
       
       //控制需要切入log的包
       @Pointcut("execution(public * ink.bitamin.controller.*.*(..))")
       public void webLog(){
   
       }
   
       @Before("webLog()")
       public void doBefore(JoinPoint joinPoint) throws Throwable {
   
           ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
           HttpServletRequest request = attributes.getRequest();
   
           logger.info("*********************** Request Detail **************************");
           logger.info("HTTP URL:" + request.getRequestURL().toString());
           logger.info("HTTP Method:" + request.getMethod());
           logger.info("HTTP IP:" + request.getRemoteAddr());
           logger.info("Class Method:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
           logger.info("Class Arguments:" + Arrays.toString(joinPoint.getArgs()));
           logger.info("Request QueryString:" + request.getQueryString());
   
       }
   
       @Around("webLog()")
       public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
           long startTime = System.currentTimeMillis();
           Object object = proceedingJoinPoint.proceed();
           logger.info("Time:" + (System.currentTimeMillis() - startTime) + "ms");
           return object;
       }
   
       @AfterReturning(returning = "result", pointcut = "webLog()")
       public void doAfterReturning(Object result) throws Throwable {
           logger.info("response:" + result);
           logger.info("***********************  Request End  ***************************");
       }
   }
   ```
   
   
   
3. 编写一个控制器调用Service和使用PageHelper插件进行分页查询，然后返回MessageVO对象

   ```java
   @RestController//RESTFul风格方式
   public class AccountController {
       @Autowired
       AccountService accountService;
   
     	//当使用RESTFul风格的时候，传入实体的时候，id需要跟实体id参数一致才会被识别
       @RequestMapping(value = "/account/users/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json")
       public MessageVO getAccounts(
               @RequestBody Map<String, String> map,
               @PathVariable Integer pageNum,
               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
           System.out.println(map.get("password"));
           PageHelper.startPage(pageNum, pageSize);
           List<AccountVO> allWithDepartment = accountService.getAllWithDepartment();
           PageInfo pageInfo = new PageInfo(allWithDepartment,9);
           return MessageVO.success().add("pageInfo", pageInfo);
       }
   }
   
   
   @Controller
   public class AccountController {
       @Autowired
       AccountService accountService;
     
     	//JSON返回方式,需要jackson支持
       @ResponseBody
       @RequestMapping(value = "/account/users", method = RequestMethod.POST)
       public MessageVO getAccounts(
               @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
         //PageHelper.startPage后的代码都是分页查询
         PageHelper.startPage(pageNum, pageSize);
         //使用service获取列表数据
         List<AccountVO> allWithDepartment = accountService.getAllWithDepartment();
         //用PageInfo对列表结果进行包装
   			PageInfo pageInfo = new PageInfo(allWithDepartment,9);
         //封装进MessageVO对象进行返回{code:200, msg:"",data:{}}
   			return MessageVO.success().add("pageInfo", pageInfo);
       }
     
     	//视图方式
   		@RequestMapping(value = "/account/users", method = RequestMethod.GET)
       public String getAccounts(
               @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
       				Model model){
         //PageHelper.startPage后的代码都是分页查询
         PageHelper.startPage(pageNum, pageSize);
         //使用service获取列表数据
         List<AccountVO> allWithDepartment = accountService.getAllWithDepartment();
         //用PageInfo对列表结果进行包装
   			PageInfo pageInfo = new PageInfo(allWithDepartment,9);
         //将pageInfo放入model请求域
         model.addAttribute("pageInfo", pageInfo);
         //封装进MessageVO对象进行返回{code:200, msg:"",data:{}}
   			return "list";
       }
   }
   ```

   SpringMVC参数的四种接收方式和额外三种接收类型

   ```java
   //@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。使用该注解的类所有方法都返回对象不返回视图
   @RestController
   public class ClassName{}
   
   //表示将内容或对象作为 HTTP 响应正文返回，设置返回类型
   @ResoponseBody
   public void method(){}
   
   //List接收数组
   @RequestBody List<Accomodation> list
   //Map接收JSON对象 前端用body传参数的情况
   @RequestBody Map<String, String> map
   //Json对象跟Java实体类能一一对应的对象
   @RequestBody Entity entity
   
   /** 参数
   defaultValue 如果本次请求没有携带这个参数，或者参数为空，那么就会启用默认值
   name 绑定本次参数的名称，要跟URL上面的一样
   required 这个参数是不是必须的
   value 跟name一样的作用，是name属性的一个别名
   **/
   
   //@RequestParam http://localhost:8080/springmvc/hello/101?param1=10&param2=20
   @RequestMapping("/hello/101")
       @RequestParam(value="param1", required=true) String param1,
       @RequestParam(value="param2", required=false) String param2){
   .......
   }
   
   //@PathVariable http://localhost:8080/springmvc/hello/101?param1=10&param2=20
   @RequestMapping("/hello/{id}")
       public String getDetails(
       @PathVariable(value="id") String id,
       @RequestParam(value="param1", required=true) String param1,
       @RequestParam(value="param2", required=false) String param2){
   .......
   }
   
   //@RequestBody http://localhost:8080/springmvc/hello/101?param1=10&param2=20
   @RequestBody(value="param1", required=true) String param1,
   @RequestBody(value="param2", required=true) String param2
   ```

   

4. 2

### 单元测试

1. 传统写法测试需要手动获取IOC容器

   ```java
   ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
   DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
   ```

2. 使用Spring框架项目可以用SpringTest进行测试可以直接注入

   ```java
   //首先声明Junit的RunWith注解指定单元测试
   @RunWith(SpringJUnit4ClassRunner.class)
   //然后导入SpringTest的pom后使用@ContextConfiguration注解指定Spring配置文件
   @ContextConfiguration(locations = {"classpath:applicationContext.xml"})
   public class ClassName {
     
     @Autowired
     AccountMapper accountMapper;
     
     //可以使用普通开发的时候的AutoWired注解从IOC容器获取Bean
     public void test(){
       //获取数据库中的account列表循环打印
   		AccountExample accountExample = new AccountExample();
       List<AccountVO> accounts = accountMapper.selectByExampleWithDepartment(accountExample);
       accounts.forEach(item-> System.out.println(item));
     }
   }
   ```

3. 使用Mock模拟请求Spring前端控制器获取返回数据

   ```java
   @RunWith(SpringJUnit4ClassRunner.class)
   //配置WebAppConfiguration注解才可以获取IOC容器，用于声明一个ApplicationContext集成测试加载WebApplicationContext。作用是模拟ServletContext
   @WebAppConfiguration
   //配置Spring配置文件和SpringMVC前端控制器配置文件，因为controller，component等都是使用注解，需要注解指定spring的配置文件，扫描相应的配置，将类初始化等。
   @ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:dispatcherServlet.xml"})
   //添加事务 如果为true则涉及数据库操作时会回滚
   @Rollback(value = true)
   @Transactional
   public class MVCTest {
   
       @Autowired
       WebApplicationContext webApplicationContext;
   
       MockMvc mockMvc;
   
       @Before
       public void initMockMvc() {
       		//初始化MockMvc
           mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
       }
     
   		//Restful方式+3种传参 url param content
   		@Test
       public void pageMvcRestfulTest() throws Exception {
         	Map<String, String> map = new HashMap<>();
           map.put("password", "word");
           String jsonString = JSONObject.toJSONString(map);
         
           MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                   .get("/account/users/pageNum/1")
                   .contentType(MediaType.APPLICATION_JSON)
                   .param("pageSize", "12")
              			.content(jsonString))
                   .andDo(print())
                   .andExpect(status().isOk())
                   .andReturn();
           String contentAsString = mvcResult.getResponse().getContentAsString();
           System.out.println(contentAsString);
       }
     
       @Test//json方式
       public void pageMvcJsonTest() throws Exception {
           MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                   .post("/account/users")				//发送get请求和设置请求内容类型
                   .contentType(MediaType.APPLICATION_JSON)
                   .param("pageNum", "1")				//使用param传参数
                   .param("pageSize", "10"))			
             			.andDo(print())								//打印出请求和相应的内容
                   .andExpect(status().isOk())		//返回的状态是200
                   //.andExpect(view().name("/account/users"))//验证视图
                   .andReturn();									//获取返回数据
   				String contentAsString = mvcResult.getResponse().getContentAsString();
         	System.out.println(contentAsString);
       }
     
       @Test//视图方式
       public void pageMvcViewTest() throws Exception {
         	//发送get请求，使用param传参数，使用andReturn获取返回数据域mvcResult
           MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                 .get("/account/users")
                                                 .param("pageNum", "1")
                                                 .param("pageSize", "10"))
           																			.andReturn();
       		//获取请求对象
         	MockHttpServletRequest request = mvcResult.getRequest();
         	//获取请求中的Attribute属性 （请求域中的数据）
         	PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
         	//打印
         	System.out.println(pageInfo);
       }
     
   }
   ```

   方法解析

   - **perform**：**执行一个RequestBuilder请求**，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
   - **get**:声明发送一个get请求的方法。MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables)：根据uri模板和uri变量值得到一个GET请求方式的。另外提供了其他的请求的方法，如：post、put、delete等。
   - **param**：添加request的参数，如上面发送请求的时候带上了了pageNum = 1的参数。假如使用需要**发送json数据格式**的时将不能使用这种方式，可见pageMvcJsonTest的使用方法
   - **andExpect**：添加ResultMatcher验证规则，验证控制器执行完成**后**结果是否正确（对返回的数据进行的判断）；
   - **andDo**：添加ResultHandler结果处理器，比如调试时打印结果到控制台（对返回的数据进行的判断）；
   - **andReturn**：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理（对返回的数据进行的判断）；

4. 

###  