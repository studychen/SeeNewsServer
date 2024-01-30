#SeeNewsServer

Server side of personal news APP, Java Servlet + Mysql implementation

The first version was hosted on Sina Cloud and later transferred to Alibaba Cloud.
Pictures are stored in Qiniuyun CDN

JavaServlet+Mysql
## Development records
Online log monitoring system
Yesterday's updated news data will be sent to your mailbox at 10 o'clock every day
Modify the problem of split method returning a single element for [][""]
Initialization method: From the first page to the last page, 53 records per page, crawling news<br>
If it is interrupted midway, breakpoint initialization is required. The method is:<br>
Get the smallest id from the database, and then find out which page of the website the id is on<br>
Crawl news records below this location<br>

## Random Image API

[http://7xr4g8.com1.z0.glb.clouddn.com/671](http://7xr4g8.com1.z0.glb.clouddn.com/671) Get pictures

![blog.csdn.net/never_cxb](http://7xr4g8.com1.z0.glb.clouddn.com/671)

671 is a numerical number. Currently, the valid icon numbers are 0 to 964. Random pictures can be obtained by randomly generating IDs.

```
Random randrom = new Random(47);
String url = "http://7xr4g8.com1.z0.glb.clouddn.com/" +randrom.nextInt(964+1);
```

## mysql Create table statement

Modify table type and length according to `Exception: Data too long for column`<br>
title Longer example: <br>
"Intelligent Perception and Image Understanding" Key Laboratory of the Ministry of Education The 15th Academic Week and Brain-like Computing and Big Data Deep Learning Frontier Forum
source Longer example: Key Laboratory of Antenna and Microwave Technology
The final table field type and length are as follows:

```
CREATE TABLE `rotation` (
  `id` int(11) NOT NULL,
  `image_urls` text,
  `title` varchar(100) DEFAULT NULL,
  `publish_date` date NOT NULL,
  `read_times` int(11) NOT NULL,
  `source` varchar(50) DEFAULT NULL,
  `body` longtext,
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

##Feature list

### Crawling exception email notification
Based on JavaMail, send email notifications for abnormal pictures and abnormal URLs

#### Abnormal image url
>Normal path of the picture `/uploads/image/20160109/20160109***.jpg`
Old path `/uploads/old/201152**.jpg`


| News id | Abnormal image link | Description |
| ------------- |-------------| -----|
|  7798 | `src="/Public/kindeditor/php/`<br>`../../../uploads/image/2015**.jpg"`| 多了`/Public/kindeditor/php/`<br>前面需加上`http://see.xidian.edu.cn` |
|  7302 | `<img src="file://C:\Users\ADMINI~1\AppData`<br>`\Local\Temp\%W@GJ$ACOF(TYDYECOKVDYB.png">`| 图片资源不存在<br>忽略 |
|  7017 | `src="http://see.xidian.edu.cn/`<br>`uploads/image/20141021/20**.jpg"`| 绝对路径开头 |


### Reused file download icon
| Icon | Original address | Qiniu key value |
| ------------- |------------| -----|
|  <img border="0" src="http://7xq7ik.com1.z0.glb.clouddn.com/912720f605b84070e223d0dab690a114" width="18" heigh="18">  | http://rsc.xidian.edu.cn/plus/img/addon.gif<br>http://see.xidian.edu.cn/uploads/old/ico/zip.jpg<br>http://xgc.xidian.edu.cn/images/mid.gif<br>http://jwc.xidian.edu.cn/images/ico/rar.jpg<br>http://202.117.120.88/images/download.gif<br>The resource does not exist, use the above gif instead| `912720f605b84070e223d0dab690a114`<br>`3949a245e521f81ffd18e5d01347a20d`<br>`2a8eac72c3697a837dd66e9e5243a089`<br>`bc87e43d342b380a2145ee1bb8298759`<br>`f7324b0d360946315ac83fb8f2703044`<br>The key for each link |
|  <img border="0" src="http://7xq7ik.com1.z0.glb.clouddn.com/b5805b46ce8cf9c634b3820a23d64ca6" width="18" heigh="18"> |    http://see.xidian.edu.cn/uploads/old/file/doc.gif<br>http://jwc.xidian.edu.cn/images/ico/doc.jpg<br>http://see.xidian.edu.cn/uploads/old/ico/doc.jpg | `b5805b46ce8cf9c634b3820a23d64ca6`<br>`f8d0fc587a7c7295835e8094af094d2d`<br>`ad5d0e0cf63834756dde3dc5e9629d8` |
|  <img border="0" src="http://7xq7ik.com1.z0.glb.clouddn.com/84b7028179e09614540cea8dd0122c3c" width="18" heigh="18"> |    http://see.xidian.edu.cn/uploads/old/file/xls.gif<br>http://jwc.xidian.edu.cn/images/ico/xls.jpg<br>http://zzb.xidian.edu.cn/new/WebEdit/sysimage/icon16/xls.gif    | `84b7028179e09614540cea8dd0122c3c`<br>`d72210a72c0e174245a65e8755f6eaa`<br>`1323ef50b1457274c914413b067e9192`|

 
#### Collected exception href:

| News id | Dirty data | Description |
|------------- |-------------| -----|
| - | `href="Electronic Academy"`| href is Chinese |
| 7837 | `/uploads/file/20151202/20151202101309_73187.zip` | The same href appears multiple times<br> resulting in multiple substitutions<br>`http://see.xidian.edu.cnhtt`<br>` p://see.xidian.edu.cn/**.zip`|
| 7710 | `href="Cultivation project application related documents" ` | href is Chinese|
| - | `href="601240943@qq.com"`| Only email address<br>without the preceding "mailto:"
| - | `kb.xidian.cc `|Does not start with http|
| 6283 | `https://mail.google.com/mail/h/**`| https starts with|
| 6206 | `ftp://linux.xidian.edu.cn`| ftp starts with |


Note: Regular href starts with http https

 
### Upload pictures to Qiniu Cloud

Asynchronously upload pictures to Qiniu Cloud

### Thanks to open source, dependent class libraries
- Java crawler [Jsoup](https://github.com/jhy/jsoup)
- json serialization [gson](https://github.com/google/gson)
- Processing arrays [commons-lang](https://github.com/apache/commons-lang)
- javamail [javamail](https://java.net/projects/javamail/pages/Home)
- Chinese word segmentation [jcseg](http://www.oschina.net/p/jcseg)
- Full-text search engine toolkit [lucene](http://lucene.apache.org/)
- Random image API [unsplash](https://unsplash.it/)
