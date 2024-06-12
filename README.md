# 程序设计实践平台(PPP)

## DDL

```SQL
create table files
(
    id          int auto_increment
        primary key,
    uploader    bigint null comment '上传的用户id',
    upload_time bigint null comment '上传的时间戳',
    file_name   varchar(255) not null,
    data        longblob     not null
) collate = utf8mb4_unicode_ci;

create table problem
(
    id            bigint not null comment '题目id'
        primary key,
    creator       bigint not null comment '创建者id',
    create_time   bigint not null comment '创建时间',
    update_time   bigint null comment '修改时间',
    description   json null comment '题目描述',
    tags          json null comment 'json数组，表示题目标签',
    solution      json null comment '题解',
    judge_config  json null comment '内存时间限制,题目类',
    judge_inputs  json null comment '输入样例',
    judge_outputs json null comment '输出样例',
    title         varchar(255) null,
    visible       tinyint null
) collate = utf8mb4_unicode_ci;

create index create_user
    on problem (creator);

create table problem_sets
(
    id          bigint not null comment '题目id'
        primary key,
    create_user bigint not null comment '创建者id',
    create_time bigint not null comment '创建时间',
    update_time bigint null comment '修改时间',
    open_time   bigint null comment '开放时间',
    info        json null comment '题目集描述',
    end_time    bigint null
);

create index create_user
    on problem_sets (create_user);

create table problem_sets_problem
(
    problem_sets_id bigint not null comment '题目集id',
    problem_id      bigint not null comment '题目id',
    primary key (problem_sets_id, problem_id)
);

create index problem_sets_id
    on problem_sets_problem (problem_sets_id);

create table problem_sets_users
(
    problem_sets_id bigint not null comment '题目集id',
    user_id         bigint not null comment '用户id',
    nickname        varchar(256) null comment '用户在题目集的名称',
    primary key (problem_sets_id, user_id)
);

create index problem_sets_id
    on problem_sets_users (problem_sets_id);

create table submission
(
    id              bigint  not null comment '提交记录id'
        primary key,
    problem_id      bigint  not null comment '题目id',
    user_id         bigint  not null comment '用户id',
    code            text null comment '用户提交的代码',
    lang            tinyint not null comment '1:java8,2:java17,3:cpp20,4:python3.8',
    submission_time bigint null comment '提交题目时间戳',
    status          tinyint null comment '枚举类型，提交状态，枚举内容看表头注释',
    exec_time       int null comment '执行时间(ms)',
    exec_memory     int null comment '消耗内存(kb)',
    problem_sets_id bigint null comment '属于那个题目集的提交'
);

create index problem_id
    on submission (problem_id);

create index problem_sets_id
    on submission (problem_sets_id);

create index user_id
    on submission (user_id);

create index user_id_2
    on submission (user_id, problem_id);

create table users
(
    id          bigint       not null comment '用户id'
        primary key,
    username    varchar(255) null comment '用户名',
    password    varchar(255) not null comment '密码',
    auth        tinyint      not null comment '枚举1:admin,2:student,3:teacher',
    create_time bigint       not null comment '时间戳，创建时间',
    update_time bigint null comment '时间戳，修改时间',
    constraint username
        unique (username)
);

create index username_2
    on users (username);


```

## 遇到的问题

### 题目的可见性设计

1. 方案一，每次查询一个题目是否可见，都去找一遍这个题目被哪些题目集给使用了，性能比较低
2. 方案二，给problem加上一个visible字段，但是，这样的话，题目结束需要给这个题目修改可见性
