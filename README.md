# SpringBoot
MySpringbootDemo


   使用代码前一般都要匹配数据库，有服务器的IP地址，如果导入失败的可以用自
己的代码，只是希望自己的代码能帮助需要的人，给大家一个基础学习小小帮助~


springboot_01_web_redis：
	整合了redis，查询时候会先从缓存中获取数据，使用时候先修改相关数据库信息
	
springboot_02_web_Async_email_time：
	整合了同步、异步、定时任务，使用邮箱发送的案例，在使用该代码前涉及到邮箱
填写的地方需要手动修改，本人修改了代码提示...一定要改完对应的发送邮件信息

springboot_03_web_rabbitMQ：
	整合了rabbitMQ消息队列，在使用该代码前，要知道消息队列的操作顺序，怎么在
服务器中通过交换器、通过绑定条件获得对应的消息队列信息，用前注意修改rabbitMQ
的服务器端口，用户名以及密码（默认是guest，5672）

springboot_04_web_elasticsearch：
	整合了elasticsearch的简单使用，实现索引的添加删除，文档的增删改查，大量查
询，大量插入，带有注释，看起来不难，就是不太完善，在ElasticSearchClientConfig
类中修改服务器IP。 已经添加天猫的搜索记录添加（中文效果不佳），高亮显示（尚未测试）


springboot_05_web_zookeeper_dubbo：
	整合了zookeeper和dubbo，在application.properties文件中对应应用名称，启动端
口号，以及同样的注册中心（zookeeper）地址。

springboot_06_web_mybatis：
	整合了mybatis、redis缓存，没事做个分页来着，有使用limit做分页，也有pagehelper
做的分页，可以拿去看看，忘记修改了啥，反正启动前记得导入sql文件，做对应的数据库，
修改IP地址。

springboot_07_web_AliyunDysms：
	整合了阿里云服务器的短信服务，可以通过服务器向用户发送验证码起到登录验证、注册验证
功能，使用前记得修改对应的模板code，标签，手机号码的等相关信息，如需使用redis记得修改对应的
redis服务器地址，并且打开redis服务器服务。


