/*
 AI知识库表结构
 存储AI相关的知识条目，包括问题、答案、分类等信息
*/

DROP TABLE IF EXISTS `t_ai_knowledge`;
CREATE TABLE `t_ai_knowledge`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '知识库id',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '知识标题',
  `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '问题内容',
  `answer` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '答案内容',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'general' COMMENT '知识分类',
  `tags` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签（逗号分隔）',
  `difficulty` tinyint(1) NOT NULL DEFAULT 1 COMMENT '难度等级 (1初级 2中级 3高级)',
  `view_count` int NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `like_count` int NOT NULL DEFAULT 0 COMMENT '点赞次数',
  `is_public` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否公开 (0否 1是)',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 (0草稿 1已发布 2已归档)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category`(`category`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI知识库表' ROW_FORMAT = DYNAMIC;

-- 插入示例数据
INSERT INTO `t_ai_knowledge` VALUES 
(1, 'Spring Boot基础介绍', '什么是Spring Boot？', 
'Spring Boot是一个基于Spring框架的开源Java应用开发框架，它简化了Spring应用的初始搭建以及开发过程。Spring Boot的主要特点包括：\n\n1. 自动配置：Spring Boot会根据添加的jar依赖自动配置Spring应用\n2. 起步依赖：提供一系列starter依赖，简化Maven配置\n3. 内嵌服务器：可以直接运行，无需部署到外部容器\n4. 生产就绪：提供健康检查、度量等生产环境功能\n\n使用Spring Boot可以快速构建独立的、生产级别的Spring应用。', 
'Java', 'Spring Boot,Java,后端开发', 1, 0, 0, 1, 1, NOW(), NULL),

(2, 'MyBatis Plus使用指南', '如何在项目中集成MyBatis Plus？', 
'MyBatis Plus是MyBatis的增强工具，在MyBatis的基础上只做增强不做改变。集成步骤：\n\n1. 添加依赖\n```xml\n<dependency>\n    <groupId>com.baomidou</groupId>\n    <artifactId>mybatis-plus-boot-starter</artifactId>\n    <version>3.4.3</version>\n</dependency>\n```\n\n2. 配置数据源（application.yml）\n```yaml\nspring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/db_name\n    username: root\n    password: password\n```\n\n3. 创建Mapper接口继承BaseMapper\n```java\npublic interface UserMapper extends BaseMapper<User> {\n}\n```\n\n这样就可以使用MyBatis Plus提供的CRUD方法了。', 
'Java', 'MyBatis,数据库,ORM', 2, 0, 0, 1, 1, NOW(), NULL),

(3, 'Redis缓存策略', 'Redis有哪些常见的缓存策略？', 
'Redis常见的缓存策略包括：\n\n1. **缓存穿透**：查询不存在的数据，解决方案：\n   - 布隆过滤器\n   - 缓存空对象\n\n2. **缓存击穿**：热点数据过期，解决方案：\n   - 设置热点数据永不过期\n   - 加互斥锁\n\n3. **缓存雪崩**：大量缓存同时过期，解决方案：\n   - 设置随机过期时间\n   - 使用Redis集群\n\n4. **数据一致性**：\n   - 先更新数据库，再删除缓存\n   - 延时双删策略\n\n合理使用这些策略可以提高系统性能和可靠性。', 
'数据库', 'Redis,缓存,性能优化', 2, 0, 0, 1, 1, NOW(), NULL);
