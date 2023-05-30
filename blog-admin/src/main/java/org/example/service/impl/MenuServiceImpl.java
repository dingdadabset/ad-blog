package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Strings;
import org.example.conf.ResponseResult;
import org.example.conf.SecurityUtils;
import org.example.conf.SystemConstants;
import org.example.entity.Menus;
import org.example.entity.MenusPro;
import org.example.mapper.MenuMapper;
import org.example.entity.Menu;
import org.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-05-26 15:55:51
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            return menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {

        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult selectList(String status, String menuName) {


        //使用mabatisplus的api
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Strings.hasText(status),Menu::getStatus,status)
                .like(Strings.hasText(menuName),Menu::getMenuName,menuName);
        List<Menu> list = list(queryWrapper);
        //使用xml文件试试
        //使用mapper中的模糊查询
        return ResponseResult.okResult(list);
    }
@Autowired
private MenuServiceImpl menuService;
    @Override
    public ResponseResult getTreeById(String id) {
        Menu byId = getById(id);
        List<Menus> tree = menuService.getTree(id);
        Menus build = Menus.builder().label(byId.getMenuName())
                .parentId(byId.getParentId().toString())
                .children(tree).build();
        MenusPro menusPro = new MenusPro();
        return ResponseResult.okResult(menusPro.setMenus(build););
    }
    public  List<Menus> getTree(String id){
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,id);
        List<Menu> list = list(queryWrapper);
        List<Menus> menus = new ArrayList<>();
        for (Menu menu : list) {
            Menus build = Menus.builder().label(menu.getMenuName())
                    .parentId(menu.getParentId().toString())
                    .children(getTree(menu.getId().toString())).build();
            menus.add(build);
        }

        return menus;
    }


    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {menu.setChildren(getChildren(menu, menus)); return menu;})
                .collect(Collectors.toList());
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->{m.setChildren(getChildren(m,menus));return m;})
                .collect(Collectors.toList());
        return childrenList;
    }
}

