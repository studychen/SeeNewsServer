# APISeeNews
新闻服务器端，JavaWeb 实现，基于新浪云、七牛云

Java Servlet+Mysql

##功能清单

### 爬取异常情况邮件通知
基于 JavaMail，对于异常图片、异常 url 发送邮件通知

#### 异常图像url
- 重复的附件图标 http://rsc.xidian.edu.cn/plus/img/addon.gif [新闻 id 7920]

- 脏数据 <img src="file://C:\Users\ADMINI~1\AppData\Local\Temp\%W@GJ$ACOF(TYDYECOKVDYB.png"> [新闻 id 7302]  

#### 已收集异常 href :
- <a href="mailto:601240943@qq.com">601240943@qq.com</a> 只有邮箱，没有前面的"mailto:"

- http 开头的、www 开头、kb.xidian.cc 等等

### 图片上传到七牛云

异步上传图片到七牛云

### 
