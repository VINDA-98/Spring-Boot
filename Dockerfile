 #使用镜像
FROM java:8
#绑定容器内的路径到主机     
VOLUME /var/docker/standard
#拷贝standard .jar 到容器内并且命名为app.jar           
ADD standard.jar app.jar 
#以bash方式运行jar                    
RUN bash -c 'touch /app.jar'         
#容器对外开放端口      
EXPOSE 8081                                      
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=docker"] 