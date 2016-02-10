# APISeeNews
新闻服务器端，JavaWeb 实现，基于新浪云、七牛云

Java Servlet+Mysql
## 开发记录

初始化方法：从第1页到末页，每页53条记录，爬取新闻
如果中途中断，需要断点初始化，方法是：
从数据库中取出最小的 id，然后找出该 id 在网站哪一页第几个，
爬取该位置以下的新闻记录


##功能清单

### 爬取异常情况邮件通知
基于 JavaMail，对于异常图片、异常 url 发送邮件通知

#### 异常图像url
>图片正常路径  `/uploads/image/20160109/20160109***.jpg`
旧路径 `/uploads/old/201152**.jpg`


| 新闻 id        |  异常图片链接          | 描述  |
| ------------- |-------------| -----|
|  7798 | `src="/Public/kindeditor/php/`<br>`../../../uploads/image/2015**.jpg"`| 多了`/Public/kindeditor/php/`，前面需加上`http://see.xidian.edu.cn` |
|  7302 | `<img src="file://C:\Users\ADMINI~1\AppData`<br>`\Local\Temp\%W@GJ$ACOF(TYDYECOKVDYB.png">`| 图片资源不存在，忽略 |
|  7017 | `src="http://see.xidian.edu.cn/`<br>`uploads/image/20141021/20**.jpg"`| 绝对路径开头 |


### 重复使用的文件下载图标
| 图标       |  原地址          | 七牛 key 值  |
| ------------- |------------| -----|
|  <img border="0" src="http://7xq7ik.com1.z0.glb.clouddn.com/912720f605b84070e223d0dab690a114" width="18" heigh="18">  | http://rsc.xidian.edu.cn/plus/img/addon.gif<br>http://see.xidian.edu.cn/uploads/old/ico/zip.jpg<br>http://xgc.xidian.edu.cn/images/mid.gif<br>http://jwc.xidian.edu.cn/images/ico/rar.jpg<br>http://202.117.120.88/images/download.gif<br>资源不存在，改用上面的gif| `912720f605b84070e223d0dab690a114`<br>`3949a245e521f81ffd18e5d01347a20d`<br>`2a8eac72c3697a837dd66e9e5243a089`<br>`bc87e43d342b380a2145ee1bb8298759`<br>`f7324b0d360946315ac83fb8f2703044`<br>各个链接对于的 key |
|  <img border="0" src="http://7xq7ik.com1.z0.glb.clouddn.com/b5805b46ce8cf9c634b3820a23d64ca6" width="18" heigh="18"> |    http://see.xidian.edu.cn/uploads/old/file/doc.gif<br>http://jwc.xidian.edu.cn/images/ico/doc.jpg<br>http://see.xidian.edu.cn/uploads/old/ico/doc.jpg | `b5805b46ce8cf9c634b3820a23d64ca6`<br>`f8d0fc587a7c7295835e8094af094d2d`<br>`ad5d0e0cf63834756dde3dc5e9629d8` |
|  <img border="0" src="http://7xq7ik.com1.z0.glb.clouddn.com/84b7028179e09614540cea8dd0122c3c" width="18" heigh="18"> |    http://see.xidian.edu.cn/uploads/old/file/xls.gif<br>http://jwc.xidian.edu.cn/images/ico/xls.jpg    | `84b7028179e09614540cea8dd0122c3c`<br>`d72210a72c0e174245a65e8755f6eaa`|



 
#### 已收集异常 href :

| 新闻 id        |  脏数据          | 描述  |
| ------------- |-------------| -----|
|  -    | `href="电院"`| href 是中文 |
| 7837 |  `/uploads/file/20151202/20151202101309_73187.zip`      | 同一个 href 出现多次，导致替换多次，出现`http://see.xidian.edu.cnhttp://see.xidian.edu.cn/**.zip`|
| 7710 | `href="培育项目申报相关文件" ` |  href是中文|
| - | `href="mailto:601240943@qq.com"`|  只有邮箱，没有前面的"mailto:"
| - | `kb.xidian.cc `  或者 `www.baidu.com`      |  未以 http 开头|
| 6283 | `https://mail.google.com/mail/h/**`      |  https 开头|
| 6206 | `ftp://linux.xidian.edu.cn`      |  ftp 开头|



 常规 href 以 http https 开头
### 图片上传到七牛云

异步上传图片到七牛云

### 感谢开源，依赖的类库
- Java爬虫 [Jsoup](https://github.com/jhy/jsoup)
- json 序列化 [gson](https://github.com/google/gson)
- 处理数组 [commons-lang](https://github.com/apache/commons-lang)
- javamail [javamail](https://java.net/projects/javamail/pages/Home)
