-- AI知识库菜单权限配置
-- 在文章管理模块下添加AI知识库管理菜单

-- 添加AI知识库管理菜单（一级菜单，放在文章管理下）
INSERT INTO `t_menu` VALUES (127, 1, 'C', 'AI知识库', 'knowledge', 'education', '/blog/knowledge/index', 'blog:knowledge:list', 0, 0, 5, NOW(), NULL);

-- 添加AI知识库相关按钮权限
INSERT INTO `t_menu` VALUES (128, 127, 'B', '添加知识', NULL, NULL, NULL, 'blog:knowledge:add', 0, 0, 1, NOW(), NULL);
INSERT INTO `t_menu` VALUES (129, 127, 'B', '删除知识', NULL, NULL, NULL, 'blog:knowledge:delete', 0, 0, 2, NOW(), NULL);
INSERT INTO `t_menu` VALUES (130, 127, 'B', '修改知识', NULL, NULL, NULL, 'blog:knowledge:update', 0, 0, 3, NOW(), NULL);
INSERT INTO `t_menu` VALUES (131, 127, 'B', '编辑知识', NULL, NULL, NULL, 'blog:knowledge:edit', 0, 0, 4, NOW(), NULL);
INSERT INTO `t_menu` VALUES (132, 127, 'B', '查看详情', NULL, NULL, NULL, 'blog:knowledge:detail', 0, 0, 5, NOW(), NULL);

-- 为管理员角色添加AI知识库权限
INSERT INTO `t_role_menu` SELECT NULL, '1', 127 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `t_role_menu` WHERE `role_id` = '1' AND `menu_id` = 127);
INSERT INTO `t_role_menu` SELECT NULL, '1', 128 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `t_role_menu` WHERE `role_id` = '1' AND `menu_id` = 128);
INSERT INTO `t_role_menu` SELECT NULL, '1', 129 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `t_role_menu` WHERE `role_id` = '1' AND `menu_id` = 129);
INSERT INTO `t_role_menu` SELECT NULL, '1', 130 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `t_role_menu` WHERE `role_id` = '1' AND `menu_id` = 130);
INSERT INTO `t_role_menu` SELECT NULL, '1', 131 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `t_role_menu` WHERE `role_id` = '1' AND `menu_id` = 131);
INSERT INTO `t_role_menu` SELECT NULL, '1', 132 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `t_role_menu` WHERE `role_id` = '1' AND `menu_id` = 132);
