# 便捷用户注册登录模块使用文档

## 概述

这是一个基于 SpringBoot + MyBatis Plus + MySQL + Spring Security + JWT 的便捷用户认证模块，提供了开箱即用的注册和登录功能。

## 技术栈

- **SpringBoot 2.5.0** - 核心框架
- **MyBatis Plus 3.4.3** - ORM框架
- **MySQL 8.0** - 数据库
- **Spring Security** - 安全框架
- **JWT (JSON Web Token)** - 无状态认证
- **Redis** - 会话存储

## 核心特性

### 1. 用户注册
- ✅ 自动密码加密（BCrypt）
- ✅ 密码强度验证（至少6位，包含字母和数字）
- ✅ 邮箱格式验证
- ✅ 用户名、昵称、邮箱唯一性检查
- ✅ 两次密码确认
- ✅ 实时可用性检查接口

### 2. 用户登录
- ✅ 支持用户名或邮箱登录
- ✅ JWT Token 认证
- ✅ "记住我"功能（7天有效期）
- ✅ Redis 会话管理
- ✅ 自动识别登录方式

### 3. 安全特性
- ✅ Spring Security 整合
- ✅ 密码BCrypt加密
- ✅ JWT Token 防护
- ✅ Redis 会话控制
- ✅ 登出清除会话

## API 接口文档

### 基础路径
```
/auth
```

### 1. 用户注册

**接口地址**: `POST /auth/register`

**请求头**:
```
Content-Type: application/json
```

**请求体**:
```json
{
  "username": "testuser",
  "password": "abc123",
  "confirmPassword": "abc123",
  "nickname": "测试用户",
  "email": "test@example.com"
}
```

**参数说明**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | 是 | 用户名，3-20个字符 |
| password | String | 是 | 密码，至少6位且包含字母和数字 |
| confirmPassword | String | 是 | 确认密码，必须与password一致 |
| nickname | String | 是 | 昵称，2-20个字符 |
| email | String | 是 | 邮箱，必须是有效的邮箱格式 |

**成功响应**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "注册成功"
}
```

**失败响应示例**:
```json
{
  "code": 501,
  "msg": "用户名已存在"
}
```

**可能的错误码**:
- 500: 两次输入的密码不一致
- 500: 密码至少6位且必须包含字母和数字
- 500: 邮箱格式不正确
- 501: 用户名已存在
- 512: 昵称已存在
- 503: 邮箱已存在

---

### 2. 用户登录

**接口地址**: `POST /auth/login`

**请求头**:
```
Content-Type: application/json
```

**请求体**:
```json
{
  "username": "testuser",
  "password": "abc123",
  "rememberMe": true
}
```

**参数说明**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | 是 | 用户名或邮箱 |
| password | String | 是 | 密码 |
| rememberMe | Boolean | 否 | 是否记住我，true为7天，false或不传为24小时 |

**成功响应**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "userInfo": {
      "id": 1,
      "userName": "testuser",
      "nickName": "测试用户",
      "email": "test@example.com",
      "avatar": "https://...",
      "sex": "0"
    }
  }
}
```

**失败响应**:
```json
{
  "code": 505,
  "msg": "用户名或密码错误"
}
```

**使用token**:

登录成功后，在后续请求中需要在请求头中携带token：
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

或者使用：
```
token: eyJhbGciOiJIUzI1NiJ9...
```

---

### 3. 用户登出

**接口地址**: `POST /auth/logout`

**请求头**:
```
token: eyJhbGciOiJIUzI1NiJ9...
```

**请求体**: 无

**成功响应**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "登出成功"
}
```

---

### 4. 检查用户名是否可用

**接口地址**: `GET /auth/check-username?username=testuser`

**请求参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | 是 | 要检查的用户名 |

**成功响应** (用户名可用):
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "用户名可用"
}
```

**失败响应** (用户名已被使用):
```json
{
  "code": 500,
  "msg": "用户名已被使用"
}
```

---

### 5. 检查邮箱是否可用

**接口地址**: `GET /auth/check-email?email=test@example.com`

**请求参数**:

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| email | String | 是 | 要检查的邮箱 |

**成功响应** (邮箱可用):
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "邮箱可用"
}
```

**失败响应示例**:
```json
{
  "code": 500,
  "msg": "邮箱格式不正确"
}
```

或

```json
{
  "code": 500,
  "msg": "邮箱已被使用"
}
```

---

## 快速开始

### 1. 前端注册示例

```javascript
// 注册
async function register() {
  const response = await fetch('http://localhost:8080/auth/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      username: 'testuser',
      password: 'abc123',
      confirmPassword: 'abc123',
      nickname: '测试用户',
      email: 'test@example.com'
    })
  });
  
  const result = await response.json();
  if (result.code === 200) {
    alert('注册成功');
  } else {
    alert(result.msg);
  }
}
```

### 2. 前端登录示例

```javascript
// 登录
async function login() {
  const response = await fetch('http://localhost:8080/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      username: 'testuser',
      password: 'abc123',
      rememberMe: true
    })
  });
  
  const result = await response.json();
  if (result.code === 200) {
    // 保存token到localStorage
    localStorage.setItem('token', result.data.token);
    localStorage.setItem('userInfo', JSON.stringify(result.data.userInfo));
    alert('登录成功');
  } else {
    alert(result.msg);
  }
}
```

### 3. 携带Token请求示例

```javascript
// 请求需要认证的接口
async function getUserInfo() {
  const token = localStorage.getItem('token');
  
  const response = await fetch('http://localhost:8080/user/userInfo', {
    method: 'GET',
    headers: {
      'token': token
    }
  });
  
  const result = await response.json();
  console.log(result);
}
```

### 4. 登出示例

```javascript
// 登出
async function logout() {
  const token = localStorage.getItem('token');
  
  const response = await fetch('http://localhost:8080/auth/logout', {
    method: 'POST',
    headers: {
      'token': token
    }
  });
  
  const result = await response.json();
  if (result.code === 200) {
    // 清除本地存储
    localStorage.removeItem('token');
    localStorage.removeItem('userInfo');
    alert('登出成功');
  }
}
```

### 5. 实时验证示例

```javascript
// 检查用户名是否可用（注册页面实时验证）
async function checkUsername(username) {
  const response = await fetch(`http://localhost:8080/auth/check-username?username=${username}`);
  const result = await response.json();
  
  if (result.code === 200) {
    showSuccessMessage('用户名可用');
  } else {
    showErrorMessage(result.msg);
  }
}

// 在输入框失焦时调用
document.getElementById('username').addEventListener('blur', function() {
  checkUsername(this.value);
});
```

---

## Postman 测试

### 1. 注册接口测试

```
POST http://localhost:8080/auth/register
Content-Type: application/json

Body (raw JSON):
{
  "username": "testuser001",
  "password": "test123",
  "confirmPassword": "test123",
  "nickname": "测试用户001",
  "email": "testuser001@example.com"
}
```

### 2. 登录接口测试

```
POST http://localhost:8080/auth/login
Content-Type: application/json

Body (raw JSON):
{
  "username": "testuser001",
  "password": "test123",
  "rememberMe": true
}
```

复制返回的token，在后续请求中使用。

### 3. 登出接口测试

```
POST http://localhost:8080/auth/logout
token: 这里填写登录返回的token
```

---

## 数据库表结构

用户表 `sys_user`:

```sql
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL COMMENT '昵称',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `type` char(1) DEFAULT '0' COMMENT '用户类型：0普通用户，1管理员',
  `status` char(1) DEFAULT '0' COMMENT '账号状态：0正常，1停用',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) DEFAULT NULL COMMENT '性别：0男，1女，2未知',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT '0' COMMENT '删除标志：0未删除，1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_name` (`user_name`),
  UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

---

## 安全建议

1. **生产环境配置**
   - 修改JWT密钥：在 `JwtUtil.java` 中修改 `JWT_KEY`
   - 使用HTTPS协议
   - 配置CORS跨域策略

2. **密码策略**
   - 当前密码要求：至少6位，包含字母和数字
   - 可根据需要在 `AuthServiceImpl.PASSWORD_PATTERN` 中调整正则表达式

3. **Token管理**
   - 默认token有效期：24小时
   - 记住我token有效期：7天
   - 可在 `JwtUtil.JWT_TTL` 中调整默认有效期

4. **Redis配置**
   - 确保Redis服务正常运行
   - 会话key格式：`bloglogin:{userId}`

---

## 常见问题

### Q1: 如何修改密码强度要求？

修改 `AuthServiceImpl.java` 中的 `PASSWORD_PATTERN` 常量：

```java
// 当前要求：至少6位，包含字母和数字
private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

// 修改为：至少8位，包含大小写字母、数字和特殊字符
private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
```

### Q2: 如何修改token有效期？

在 `AuthServiceImpl.login()` 方法中修改：

```java
// 记住我：7天改为30天
jwt = JwtUtil.createJWT(userId, 30 * 24 * 60 * 60 * 1000L);

// 默认：24小时改为2小时
jwt = JwtUtil.createJWT(userId, 2 * 60 * 60 * 1000L);
```

### Q3: 如何支持手机号注册登录？

1. 在 `UserRegisterDTO` 中添加 `phonenumber` 字段
2. 在 `AuthServiceImpl.register()` 中添加手机号验证和唯一性检查
3. 在 `AuthServiceImpl.login()` 中添加手机号登录逻辑

### Q4: 如何添加验证码？

建议使用图形验证码或短信验证码：
1. 引入验证码库（如 kaptcha）
2. 在注册/登录接口添加验证码验证逻辑
3. 使用Redis存储验证码，设置过期时间

---

## 核心文件位置

- **DTO**: `blog-springbooy/src/main/java/org/example/entity/`
  - `UserRegisterDTO.java` - 注册数据传输对象
  - `UserLoginDTO.java` - 登录数据传输对象

- **Service**: `blog-springbooy/src/main/java/org/example/service/`
  - `AuthService.java` - 认证服务接口
  - `impl/AuthServiceImpl.java` - 认证服务实现

- **Controller**: `blog-springbooy/src/main/java/org/example/controller/`
  - `AuthController.java` - 认证控制器

- **配置**: `blog-springbooy/src/main/java/org/example/conf/`
  - `JwtUtil.java` - JWT工具类
  - `SecurityConfig.java` - Security配置
  - `JwtAuthenticationTokenFilter.java` - JWT过滤器

---

## 总结

这个便捷的用户注册登录模块提供了：
- ✅ 开箱即用的注册和登录功能
- ✅ 完善的数据验证和安全保护
- ✅ 灵活的配置选项
- ✅ 清晰的API文档
- ✅ 丰富的使用示例

适合快速搭建需要用户认证的SpringBoot项目！
