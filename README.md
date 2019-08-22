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

7. 添加[Spring Web MVC控制器](https://mvnrepository.com/artifact/org.springframework/spring-webmvc) [Spring JDBC](https://mvnrepository.com/artifact/org.springframework/spring-jdbc) [Spring Test](https://mvnrepository.com/artifact/org.springframework/spring-test) [Spring Aspects面向切面](https://mvnrepository.com/artifact/org.springframework/spring-aspects) [MyBatis](https://mvnrepository.com/artifact/org.mybatis/mybatis) [MyBatis-Spring适配器](https://mvnrepository.com/artifact/org.mybatis/mybatis-spring) [MyBatis-Generator代码生成器](https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core) [MyBatis-PageHelper](https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper) [Mysql 数据库驱动](https://mvnrepository.com/artifact/mysql/mysql-connector-java)(Mysql数据库驱动8.0版本使用com.mysql.cj.jdbc.Driver) [数据库连接池c3p0](https://mvnrepository.com/artifact/com.mchange/c3p0) 和其他需要的依赖（mvnrepository.com中找）[alibaba fastjson](https://mvnrepository.com/artifact/com.alibaba/fastjson) [jackson-core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core) [jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind) [JSR303-HibernateVaildator](https://mvnrepository.com/artifact/org.hibernate/hibernate-validator) [servlet-api](https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api ) [jstl](https://mvnrepository.com/artifact/jstl/jstl) [Junit](https://mvnrepository.com/artifact/junit/junit) 到Pom文件中

8. 配置web.xml 引入SSM框架

   1. 配置Spring配置文件路径

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

   5. 配置Rest风格转换器(可选)

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

9. 配置applictionContext.xml，在resources下创建SpringConfig的xml文件'applictionContext.xml' (配置业务逻辑)

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
      <!--配置基于注解的事务配置或者xml方式的事务配置，正常情况均使用xml配置方式更为灵活-->
      <aop:config>
        <!--配置切入点表达式 execution(任意返回值 包名下任意包..任意类*任意方法(任意形参))-->
        <aop:pointcut id="txPoint" expression="execution(* ink.bitamin.service..*(..))"/>
        <!--配置事务增强 指向id为txAdvice-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPoint"/>
      </aop:config>
      <!--配置事务增强如何切入 如果配置事务控制的bean的id为transactionManager则transaction-manager可以不用写，因为transaction-manager的默认值就是transactionManager-->
      <tx:advice id="txAdvice" transaction-manager="dataSourceTransactionManager">
        <tx:attributes>
          <!--所有方法都是事务方法-->
          <tx:method name="*"/>
          <!--以方法名为get开始的所有方法-->
          <tx:method name="get*" read-only="true"/>
        </tx:attributes>
      </tx:advice>
      
      ```

10. 配置dispatcherServlet.xml，在resources下创建SpringConfig的xml文件 (页面跳转逻辑)

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

    

11. 配置mybatisConfig.xml，在resources下创建SpringConfig的xml文件 (配置数据映射)

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

12. 配置mybatisGeneratorCore.xml，在项目根目录下创建代码生成配置的xml文件 (配置生成代码)

    1. 从Mybatis官网的[Generator工具页面](http://www.mybatis.org/generator/configreference/xmlconfig.html)获取到Mybatis-generator-core的xml配置文件并修改

       ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       <!DOCTYPE generatorConfiguration
         PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
         "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
       
       <generatorConfiguration>
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

    2. 有4种方式可以生成代码 ，最常用的有eclipse，Maven，Java方式，以下使用Java方式（[官网](http://www.mybatis.org/generator/running/runningWithJava.html)可找到该代码）

       编写完毕后运行即可生成

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

    3. 2

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

1. 配置一个返回VO封装对象

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

2. 编写一个控制器调用Service和使用PageHelper插件进行分页查询，然后返回MessageVO对象

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

   

3. 2

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