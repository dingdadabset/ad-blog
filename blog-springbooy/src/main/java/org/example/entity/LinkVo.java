package org.example.entity;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.entity
 * @Author: dingquan
 * @CreateTime: 2023-05-23  15:29
 * @Description: TODO
 * @Version: 1.0
 */
public class LinkVo {

        private String address;
        private String description;
        private String id;
        private String logo;
        private String name;
        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
        public String getLogo() {
            return logo;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }


}
