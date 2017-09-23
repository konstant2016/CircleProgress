# CircleProgress
一个环形进度条</br>

示例图片一：</br>
![img](https://github.com/konstant2016/CircleProgress/blob/master/2017-09-22-10mz%E7%A4%BA%E4%BE%8B%E5%9B%BE%E7%89%87%E4%B8%80.gif)

示例图片二：</br>
![img](https://github.com/konstant2016/CircleProgress/blob/master/2017-09-22-10mz%E7%A4%BA%E4%BE%8B%E5%9B%BE%E7%89%87%E4%BA%8C.gif)


xml使用介绍：</br>
</br>
app:upperColor1="#000000"       上层圆的第一渐变色</br>
app:upperColor2="#000000"       上层圆的第二渐变色</br>
app:upperColor3="#000000"       上层圆的第三渐变色</br>
app:upperLineWidth="15dp"       上层圆的线宽</br>
app:lowerColor="#C6E2FF"        背景圆的颜色</br>
app:lowerLineWidth="15dp"       背景圆的线宽</br>
app:shadowLineWidth="30dp"      阴影的线宽</br>
app:shadowColor="#30000000"     阴影的颜色</br>
app:startAngle="270"            起始角度</br>
app:sweepAngle="360"            扫描角度（注意：不是结束角度）</br>
app:value="2000"                上层圆的进度值</br>


java代码使用的api：</br>
</br>
public void setValue(float value)           设置上层圆的进度值（最大值为10000，请按照比例设置进度值）设置后立即生效</br>
public void setUpperColors(int[] colors)    设置渐变色的数组，最多设置三种颜色，如果只需要两种颜色，请把第二、第三种颜色保持一致，colors为颜色的数组</br>
public void setUpperLineWidth(int width)    设置上层圆的线宽</br>
public void setLowerLineWidth(int width)    设置背景圆的线宽</br>
public void setShadowLineWidth(int width)   设置阴影线宽</br>