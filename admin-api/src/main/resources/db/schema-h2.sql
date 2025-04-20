-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(64) DEFAULT NULL COMMENT '用户名',
                         password VARCHAR(64) DEFAULT NULL COMMENT '密码',
                         avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
                         remark VARCHAR(255) DEFAULT NULL COMMENT '备注信息',
                         create_time TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                         update_time TIMESTAMP DEFAULT NULL COMMENT '最后修改时间',
                         deleted BOOLEAN DEFAULT FALSE COMMENT '删除标记'
);

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO admin VALUES (1, 'admin', '$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG', NULL, '超级管理员', NULL, NULL, FALSE);
INSERT INTO admin VALUES (2, 'user1', '$2a$10$UdHHnrhDt/pe0TZAtlqxw.WlmwLiUS7Iu0rWNvG59ojow7ry56sla', NULL, '图书管理员', NULL, '2019-05-28 03:07:32', FALSE);
INSERT INTO admin VALUES (3, 'auditors', '$2a$10$6hjIegmba23ppUeCLKgqieVdiSiaIjQlAUxUGN394g2JU5ZJlGPRC', NULL, '审计人员', NULL, NULL, TRUE);
COMMIT;

-- ----------------------------
-- Table structure for admin_log
-- ----------------------------
DROP TABLE IF EXISTS admin_log;
CREATE TABLE admin_log (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             admin VARCHAR(20) DEFAULT NULL COMMENT '管理员',
                             path VARCHAR(255) DEFAULT NULL COMMENT '请求地址',
                             create_time TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                             action VARCHAR(50) DEFAULT NULL COMMENT '动作',
                             type BOOLEAN DEFAULT NULL COMMENT '日志类型',
                             result VARCHAR(2000) DEFAULT NULL COMMENT '操作结果',
                             ip VARCHAR(30) DEFAULT NULL COMMENT 'IP地址'
);


-- ----------------------------
-- Table structure for admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS admin_role_relation;
CREATE TABLE admin_role_relation (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       admin_id INT DEFAULT NULL,
                                       role_id INT DEFAULT NULL
);

-- ----------------------------
-- Records of admin_role_relation
-- ----------------------------
INSERT INTO admin_role_relation VALUES (1, 1, 1);
INSERT INTO admin_role_relation VALUES (2, 2, 2);
INSERT INTO admin_role_relation VALUES (3, 3, 2);
INSERT INTO admin_role_relation VALUES (4, 3, 3);
INSERT INTO admin_role_relation VALUES (5, 4, 3);
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS permission;
CREATE TABLE permission (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) DEFAULT NULL,
                              permission VARCHAR(255) DEFAULT NULL,
                              parent_id INT DEFAULT NULL,
                              type INT DEFAULT NULL
);

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO permission VALUES (1, '所有权限', '*', 0, 0);
INSERT INTO permission VALUES (2, '系统管理', 'sys', 0, 1);
INSERT INTO permission VALUES (3, '用户管理', 'sys:admin', 2, 1);
INSERT INTO permission VALUES (4, '用户管理列表', 'sys:admin:list', 3, 2);
INSERT INTO permission VALUES (5, '查看用户信息', 'sys:admin:read', 3, 2);
INSERT INTO permission VALUES (6, '创建用户', 'sys:admin:create', 3, 2);
INSERT INTO permission VALUES (7, '修改用户', 'sys:admin:update', 3, 2);
INSERT INTO permission VALUES (8, '删除用户', 'sys:admin:delete', 3, 2);
INSERT INTO permission VALUES (9, '日志', 'sys:log', 2, 1);
INSERT INTO permission VALUES (10, '日志列表', 'sys:log:list', 9, 2);
INSERT INTO permission VALUES (11, '图书管理', 'book.manage', 0, 1);

COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS role;
CREATE TABLE role (
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255) DEFAULT NULL,
                        description VARCHAR(255) DEFAULT NULL,
                        create_time TIMESTAMP DEFAULT NULL,
                        deleted TINYINT DEFAULT NULL,
                        update_time TIMESTAMP DEFAULT NULL,
                        PRIMARY KEY (id)
);

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role VALUES (1, '系统管理员', '系统管理员，具有系统全部权限', '1900-01-01 00:00:00', 0, NULL);
INSERT INTO role VALUES (2, '角色2', '具有系统部分权限', '1900-01-01 00:00:00', 0, NULL);
INSERT INTO role VALUES (3, '角色3', '具有系统部分权限', '1900-01-01 00:00:00', 0, NULL);
INSERT INTO role VALUES (4, 'role test1', 'role test1 description 1111', '2019-05-21 02:57:10', 0, '2019-05-28 02:40:07');
INSERT INTO role VALUES (5, 'role test2', '', '2019-05-21 02:58:39', 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS role_permission_relation;
CREATE TABLE role_permission_relation (
                                            id INT NOT NULL AUTO_INCREMENT,
                                            role_id INT DEFAULT NULL,
                                            permission_id INT DEFAULT NULL,
                                            PRIMARY KEY (id)
);

-- ----------------------------
-- Records of role_permission_relation
-- ----------------------------
INSERT INTO role_permission_relation VALUES (1, 1, 1);
INSERT INTO role_permission_relation VALUES (8, NULL, NULL);
INSERT INTO role_permission_relation VALUES (14, 2, 2);
INSERT INTO role_permission_relation VALUES (15, 2, 3);
INSERT INTO role_permission_relation VALUES (16, 2, 4);
INSERT INTO role_permission_relation VALUES (17, 2, 7);
INSERT INTO role_permission_relation VALUES (18, 2, 9);
INSERT INTO role_permission_relation VALUES (19, 2, 10);
COMMIT;

-- ----------------------------
-- Table structure for sys_storage
-- ----------------------------
DROP TABLE IF EXISTS sys_storage;
CREATE TABLE sys_storage (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               key VARCHAR(63) NOT NULL COMMENT '文件的唯一索引',
                               name VARCHAR(255) NOT NULL COMMENT '文件名',
                               type VARCHAR(20) NOT NULL COMMENT '文件类型',
                               size INT NOT NULL COMMENT '文件大小',
                               url VARCHAR(255) DEFAULT NULL COMMENT '文件访问链接',
                               create_time TIMESTAMP DEFAULT NULL COMMENT '创建时间',
                               update_time TIMESTAMP DEFAULT NULL COMMENT '更新时间',
                               deleted BOOLEAN DEFAULT FALSE COMMENT '逻辑删除'
);

-- ----------------------------
-- Records of sys_storage
-- ----------------------------
INSERT INTO sys_storage VALUES (36, 'IRjgUppwwrMG8PTgJZui.jpg', '802758360cd659c01c5a0a9af85668b5_middle.jpg', 'image/jpeg', 6559, 'http://localhost:8081/static/IRjgUppwwrMG8PTgJZui.jpg', NULL, NULL, FALSE);
COMMIT;


DROP TABLE IF EXISTS book;
CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(17) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100),
    publish_date DATE,
    price DECIMAL(10, 2),
    category_code VARCHAR(20),
    location VARCHAR(50),
    status TINYINT NOT NULL DEFAULT 1,
    total_copies INT NOT NULL DEFAULT 1,
    available_copies INT NOT NULL DEFAULT 1,
    register_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(500),
    cover_url VARCHAR(255),
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 添加表注释(部分H2版本支持)
COMMENT ON TABLE book IS '图书信息表';

-- 添加列注释(部分H2版本支持)
COMMENT ON COLUMN book.isbn IS '国际标准书号(13位数字+连接符)';
COMMENT ON COLUMN book.title IS '书名';
COMMENT ON COLUMN book.author IS '作者';
COMMENT ON COLUMN book.publisher IS '出版社';
COMMENT ON COLUMN book.publish_date IS '出版日期';
COMMENT ON COLUMN book.price IS '定价';
COMMENT ON COLUMN book.category_code IS '分类号';
COMMENT ON COLUMN book.location IS '馆藏位置';
COMMENT ON COLUMN book.status IS '状态:1-在架,2-借出,3-维修中,4-丢失';
COMMENT ON COLUMN book.total_copies IS '总副本数';
COMMENT ON COLUMN book.available_copies IS '可借副本数';
COMMENT ON COLUMN book.register_date IS '入馆日期';
COMMENT ON COLUMN book.description IS '图书简介';
COMMENT ON COLUMN book.cover_url IS '封面图片URL';
COMMENT ON COLUMN book.create_time IS '创建时间';
COMMENT ON COLUMN book.update_time IS '更新时间';

-- 创建索引
CREATE INDEX idx_book_isbn ON book(isbn);
CREATE INDEX idx_book_title ON book(title);

