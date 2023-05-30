/**
  * Copyright 2023 json.cn 
  */
package org.example.entity;
import java.util.List;

/**
 * Auto-generated: 2023-05-30 16:13:42
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class MenusPro {

    private List<Menus> menus;
    private List<String> checkedKeys;
    public void setMenus(List<Menus> menus) {
         this.menus = menus;
     }
     public List<Menus> getMenus() {
         return menus;
     }

    public void setCheckedKeys(List<String> checkedKeys) {
         this.checkedKeys = checkedKeys;
     }
     public List<String> getCheckedKeys() {
         return checkedKeys;
     }

}