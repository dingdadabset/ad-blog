# AI知识库功能文档

## 功能概述

AI知识库模块为博客系统添加了一个专门管理AI相关知识的功能模块，允许管理员添加、编辑、删除AI知识条目，并提供前台展示和搜索功能。

## 数据库设计

### 表结构：t_ai_knowledge

| 字段名 | 类型 | 说明 | 备注 |
|--------|------|------|------|
| id | int | 主键ID | 自增 |
| title | varchar(200) | 知识标题 | 必填 |
| question | text | 问题内容 | 必填 |
| answer | longtext | 答案内容 | 必填 |
| category | varchar(50) | 知识分类 | 如：Java、数据库、前端等 |
| tags | varchar(200) | 标签 | 逗号分隔，如：Spring Boot,Java |
| difficulty | tinyint | 难度等级 | 1-初级，2-中级，3-高级 |
| view_count | int | 浏览次数 | 默认0 |
| like_count | int | 点赞次数 | 默认0 |
| is_public | tinyint | 是否公开 | 0-否，1-是 |
| status | tinyint | 状态 | 0-草稿，1-已发布，2-已归档 |
| create_time | datetime | 创建时间 | 自动填充 |
| update_time | datetime | 更新时间 | 自动填充 |

### 初始化脚本

```sql
-- 运行 doc/mysql/t_ai_knowledge.sql 创建表并插入示例数据
-- 运行 doc/mysql/t_menu_ai_knowledge.sql 添加菜单权限
```

## API接口说明

### 前台接口（blog-springbooy模块）

基础路径：`/knowledge`

#### 1. 获取知识库列表（分页）
- **接口地址**：`GET /knowledge/list`
- **请求参数**：
  - pageNum: 页码（默认1）
  - pageSize: 每页大小（默认10）
  - category: 分类（可选）
  - difficulty: 难度等级（可选）
  - keyword: 关键词搜索（可选）
- **返回数据**：分页后的知识库列表

#### 2. 获取知识库详情
- **接口地址**：`GET /knowledge/{id}`
- **路径参数**：id - 知识库ID
- **返回数据**：知识库详细信息

#### 3. 增加浏览次数
- **接口地址**：`PUT /knowledge/updateViewCount/{id}`
- **路径参数**：id - 知识库ID
- **说明**：用户查看知识详情时调用

#### 4. 点赞知识库
- **接口地址**：`POST /knowledge/like/{id}`
- **路径参数**：id - 知识库ID

#### 5. 获取所有分类
- **接口地址**：`GET /knowledge/categories`
- **返回数据**：所有知识分类列表

### 后台管理接口（blog-admin模块）

基础路径：`/content/knowledge`

#### 1. 获取知识库列表（管理）
- **接口地址**：`GET /content/knowledge/list`
- **请求参数**：同前台接口
- **说明**：管理后台可查看所有状态的知识

#### 2. 添加知识库
- **接口地址**：`POST /content/knowledge`
- **请求体**：AiKnowledgeDTO对象
```json
{
  "title": "知识标题",
  "question": "问题内容",
  "answer": "答案内容",
  "category": "Java",
  "tags": "Spring Boot,Java",
  "difficulty": 1,
  "isPublic": 1,
  "status": 1
}
```

#### 3. 更新知识库
- **接口地址**：`PUT /content/knowledge`
- **请求体**：AiKnowledgeDTO对象（需包含id）

#### 4. 删除知识库
- **接口地址**：`DELETE /content/knowledge/{id}`
- **路径参数**：id - 知识库ID

#### 5. 获取知识库详情
- **接口地址**：`GET /content/knowledge/{id}`
- **路径参数**：id - 知识库ID

#### 6. 获取所有分类
- **接口地址**：`GET /content/knowledge/categories`

## 核心类说明

### 实体类（Entity）
- **AiKnowledge**: 知识库实体类，映射数据库表t_ai_knowledge

### 视图对象（VO）
- **AiKnowledgeListVO**: 列表展示用VO，包含基本信息
- **AiKnowledgeDetailVO**: 详情展示用VO，包含完整信息

### 数据传输对象（DTO）
- **AiKnowledgeDTO**: 用于添加和更新知识库的数据传输对象

### 数据访问层（DAO）
- **AiKnowledgeDao**: 继承MyBatis Plus的BaseMapper，提供基础CRUD操作

### 服务层（Service）
- **AiKnowledgeService**: 服务接口
- **AiKnowledgeServiceImpl**: 服务实现类，包含所有业务逻辑

### 控制层（Controller）
- **AiKnowledgeController**: 前台控制器
- **AiKnowledgeAdminController**: 后台管理控制器

## 权限配置

已在菜单表中添加以下权限：

- AI知识库菜单（id: 127）
  - 添加知识（blog:knowledge:add）
  - 删除知识（blog:knowledge:delete）
  - 修改知识（blog:knowledge:update）
  - 编辑知识（blog:knowledge:edit）
  - 查看详情（blog:knowledge:detail）

管理员角色（role_id=1）已默认分配所有AI知识库权限。

## 使用示例

### 1. 前台查询知识列表
```bash
GET /knowledge/list?pageNum=1&pageSize=10&category=Java&keyword=Spring
```

### 2. 查看知识详情
```bash
GET /knowledge/1
```

### 3. 后台添加新知识
```bash
POST /content/knowledge
Content-Type: application/json

{
  "title": "Spring Boot配置文件详解",
  "question": "Spring Boot的配置文件有哪些类型？",
  "answer": "Spring Boot支持两种配置文件格式：\n1. application.properties\n2. application.yml...",
  "category": "Java",
  "tags": "Spring Boot,配置",
  "difficulty": 2,
  "isPublic": 1,
  "status": 1
}
```

## 功能特点

1. **多维度筛选**：支持按分类、难度、关键词搜索
2. **统计功能**：记录浏览次数和点赞次数
3. **状态管理**：支持草稿、已发布、已归档三种状态
4. **权限控制**：前后台分离，后台需要相应权限
5. **分类管理**：动态获取所有知识分类
6. **标签系统**：支持多标签标注

## 部署说明

1. 执行数据库初始化脚本：
   ```sql
   source doc/mysql/t_ai_knowledge.sql;
   source doc/mysql/t_menu_ai_knowledge.sql;
   ```

2. 重新编译部署项目：
   ```bash
   mvn clean package -DskipTests
   ```

3. 启动服务后，管理员可以在后台管理界面看到"AI知识库"菜单

## 扩展建议

1. 添加知识库评论功能
2. 支持知识库关联推荐
3. 添加知识库导入/导出功能
4. 支持Markdown编辑器
5. 添加知识库收藏功能
