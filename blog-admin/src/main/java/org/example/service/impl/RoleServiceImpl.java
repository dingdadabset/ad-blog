package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Strings;
import org.example.conf.ResponseResult;
import org.example.entity.RoleVo;
import org.example.entity.RoleVo2;
import org.example.mapper.RoleMapper;
import org.example.entity.Role;
import org.example.service.RoleService;
import org.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-05-26 15:56:35
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {


            //判断是否是管理员 如果是返回集合中只需要有admin
            if(id == 1L){
                List<String> roleKeys = new ArrayList<>();
                roleKeys.add("admin");
                return roleKeys;
            }
            //否则查询用户所具有的角色信息
            return getBaseMapper().selectRoleKeyByUserId(id);

    }

    @Override
    public ResponseResult selList(Integer pageNum, Integer pageSize, String roleName, String status) {

        LambdaQueryWrapper<Role> eq = new LambdaQueryWrapper<Role>().eq(Strings.hasText(status), Role::getStatus, status)
                .eq(Strings.hasText(roleName), Role::getRoleName, roleName);
        Page<Role> rolePage = new Page<Role>().setCurrent(pageNum).setSize(pageSize);
        Page<Role> page = page(rolePage, eq);
        List<Role> records = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(records, RoleVo.class);
      return ResponseResult.okResult(new RoleVo2(roleVos,roleVos.size()));
    }
}

