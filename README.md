# APISeeNews
新闻服务器端，JavaWeb 实现，基于新浪云、七牛云

Java Servlet+Mysql

##功能清单

### 爬取异常情况邮件通知
基于 JavaMail，对于异常图片、异常 url 发送邮件通知

#### 异常图像url
>图片正常路径  `/uploads/image/20160109/20160109***.jpg`
旧路径 `/uploads/old/201152**.jpg`
- 重复的附件图标 http://rsc.xidian.edu.cn/plus/img/addon.gif [新闻 id 7920]

- 脏数据 `<img src="file://C:\Users\ADMINI~1\AppData\Local\Temp\%W@GJ$ACOF(TYDYECOKVDYB.png">` [新闻 id 7302]  
- `<img alt="" src="http://see.xidian.edu.cn/uploads/image/20141021/20141021022525_88324.jpg" width="605" height="497">` [新闻 id 7017]  
- `<img src="/Public/kindeditor/php/../../../uploads/image/20151116/20151116114927_39484.jpg" alt="" width="600" height="399" title="" align="">` [新闻 id 7798]  


#### 已收集异常 href :

| 新闻 id        |  脏数据          | 描述  |
| ------------- |:-------------| -----|
|  -    | `<a href="电院" target="_blank" rel="nofollow">电院</a> `| href 是中文 |
| 7837 |  `<a class="ke-insertfile" href="/uploads/file/20151202/20151202101309_73187.zip" target="_blank">2016年大赛通知电子版及附件</a>`      | 同一个 href 出现多次，替换多次，导致出现`http://see.xidian.edu.cnhttp://see.xidian.edu.cn/uploads/file/**.zip`
 |
| 7710 | `<a class="ke-insertfile" href="培育项目申报相关文件" target="_blank">培育项目申报相关文件</a>`       |  href是中文    |
| - | `<a href="mailto:601240943@qq.com">601240943@qq.com</a>`       |  只有邮箱，没有前面的"mailto:"
| - | `kb.xidian.cc `   `www` 开头     |  未以 http 开头

 
### 图片上传到七牛云

异步上传图片到七牛云

### 
