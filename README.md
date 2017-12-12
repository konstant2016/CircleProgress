[TOC]

### 环形进度条
	包含渐变色环形进度条和带刻度的渐变色环形进度条

### 示例图片
#### 图片一：
![img](https://github.com/konstant2016/CircleProgress/blob/master/2017-09-22-10mz%E7%A4%BA%E4%BE%8B%E5%9B%BE%E7%89%87%E4%B8%80.gif)

#### 图片二：
![img](https://github.com/konstant2016/CircleProgress/blob/master/2017-09-22-10mz%E7%A4%BA%E4%BE%8B%E5%9B%BE%E7%89%87%E4%BA%8C.gif)

#### 图片三：
![img](https://github.com/konstant2016/CircleProgress/blob/master/2017-11-28-04mz%E5%B8%A6%E5%88%BB%E5%BA%A6%E7%9A%84%E5%9C%86%E7%8E%AF%E8%BF%9B%E5%BA%A6%E6%9D%A1.gif)

演示效果比较丑哈，原谅我不会选色，毕竟不是UI，主要是当时项目里面有这么个需求，实现之后，想着给大家提供个思路，就发出来了；demo的确比较丑，大小，颜色什么的，我都是随便写的，主要是提供实现思路哈

### 集成方法
###### gradle：
compile 'cn.konstant:circleprogress:1.0'
###### maven：
`<dependency>
<groupId>cn.konstant</groupId>
<artifactId>circleprogress</artifactId>
 <version>1.0</version>
 <type>pom</type>
 </dependency>`

### 使用说明
##### **环形进度条**
###### circleProgress（圆环进度条）的java代码使用的api：
1. public void setValue(float value)           设置上层圆的进度值（最大值为10000，请按照比例设置进度值）设置后立即生效
2.  public void setUpperColors(int[] colors)    设置渐变色的数组，最多设置三种颜色，如果只需要两种颜色，请把												第二、第三种颜色保持一致，colors为颜色的数组
3.  public void setUpperLineWidth(int width)    设置上层圆的线宽
4.  public void setLowerLineWidth(int width)    设置背景圆的线宽
5.  public void setShadowLineWidth(int width)   设置阴影线宽
###### circleProgress（圆环进度条）的xml使用介绍：
1.  app:upperColor1="#000000"       上层圆的第一渐变色
2.  app:upperColor2="#000000"       上层圆的第二渐变色
3.  app:upperColor3="#000000"       上层圆的第三渐变色
4.  app:upperLineWidth="15dp"       上层圆的线宽
5.  app:lowerColor="#C6E2FF"        背景圆的颜色
6.  app:lowerLineWidth="15dp"       背景圆的线宽
7.  app:shadowLineWidth="30dp"      阴影的线宽
8. app:shadowColor="#30000000"     阴影的颜色
9.  app:startAngle="270"            起始角度
10.  app:sweepAngle="360"           扫描角度（注意：不是结束角度）
11.  app:value="2000"               上层圆的进度值
##### **刻度圆环进度条**
###### sacleProgress(刻度圆环)的java代码使用的api：
1.  setProgress(int mProgress)                  设置上层圆的进度值（最大值为10000，请按照比例设置进度值）设置后												立即生效
2.  setAnimDuration(long mAnimDuration)         设置动画时间
3.  setScaleNum(int scaleNum)                   设置刻度数量
###### sacleProgress（圆环进度条）的xml使用介绍：
1. app:scaleShadowColor            阴影颜色
2. app:normalColor                 未选中的刻度颜色
3. app:scaleStartColor             已选中的刻度开始颜色
4. app:scaleStopColor              已选中的刻度结束颜色
5. app:scaleWhdth                  刻度线的宽度
6. app:scaleHeight                 刻度线的长度
7. app:scaleNumber                 刻度线的数量
8. app:shadowWidth                 阴影的线宽
9. app:animDuration                动画持续时间
10. app:progress="2000"            圆环的进度值

