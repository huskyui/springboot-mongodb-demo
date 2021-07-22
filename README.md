# 工程简介

# 延伸阅读



# mongo-docker
刚开始，本来像用docker-compose.yaml来启动mongo和mongo-express，但是mongo-express有个bug,启动的时候，无法失败 重试，而且一直没有人改动
已经有人改了这个bug,但是感觉offical image 没有重新构建  ，解决方案：[max_tries](https://github.com/taboca/mongo-express-docker/tree/max_tries_docker_env)

不想特别麻烦，于是，决定单独启动mongo

## 插入数据时
我们在创建database时，需要在database中创建user,以及对应权限
```shell
db.createUser({user:"huskyui",pwd:"dabendan",roles:[{role:"readWrite",db:"test-spring-boot-database"}]})
```

## 日志
```yaml
logging:
  level:
    org.mongodb: TRACE
```
## CRUD
CRUD相关操作都在test文件夹中
