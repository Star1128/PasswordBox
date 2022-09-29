# PasswordBox
 密码保管箱
 由于是个人开发的小工具，同时也能锻炼一下自身的工程能力，项目中避免使用第三方库、UI 组件等等，尽量通过学习其原理，自行实现功能
 开发方式为主干开发，发版拉新分支存档

## 更新日志

2.0

- 数据存储方式由SharedPreferences升级为SQLite数据库，并使用了ORM框架Room
- 增添了主页面的下拉刷新功能

2.1

- 首页Item添加账号名展示
- 更换重要性等级标识图片
- 优化数据库结构，数据库保存重要性等级，取代之前的资源ID，使后续开发更为灵活
- 首页UI优化

2.2

- Add页面UI优化
- 主页面列表按重要性排序

2.3

- 主页Item长按显示密码

2.4

- 主页面Item添加删除按钮

3.0

- 引入加密算法

3.1

- 中文排序

3.2

- 中文排序回退
- 加入自定义映射，记录密码更便捷
- 优化UI


## 优化计划

!!!导出导入 因为 jks 文件丢失的原因，3.2 版本与先前版本签名不一致

!!侧滑删除

!!Add页面重要性选择UI优化

!!搜索功能

!字体引入

!根据不同重要性等级使用不同密码加密策略
