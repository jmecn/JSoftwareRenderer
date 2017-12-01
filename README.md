# 基于 Java 的软件光栅化渲染器

## 缘起

[如何开始用 C++ 写一个光栅化渲染器？ - Milo Yip的回答 - 知乎](https://www.zhihu.com/question/24786878/answer/29039253)

首先，如果从学习角度出发，不必一开始完全根据现时GPU的架构及概念，来用软件复制一遍。现时的GPU主要是基于三角形光栅化及z-buffer。

如果我们从图形学的历史进程来学习，可以这样做练习：

2D部分：

1. 光栅化2D点（就是在二维数组上画点，了解色彩的基本原理，并解决图像的输出问题）。
2. 光栅化2D直线（[布雷森汉姆直线演算法](https://zh.wikipedia.org/wiki/%E5%B8%83%E9%9B%B7%E6%A3%AE%E6%BC%A2%E5%A7%86%E7%9B%B4%E7%B7%9A%E6%BC%94%E7%AE%97%E6%B3%95)、[吴小林直线算法](https://zh.wikipedia.org/wiki/%E5%90%B4%E5%B0%8F%E6%9E%97%E7%9B%B4%E7%BA%BF%E7%AE%97%E6%B3%95)等）
3. 2D直线的剪切算法（见[Line clipping](http://en.wikipedia.org/wiki/Line_clipping)）。
4. 光栅化2D三角形（scan conversion）。避免重复光栅化相邻三角形边界的像素（edge equation）。
5. 光栅化简单/复杂多边形

3D部分：

1. 把顶点从三维世界空间变换至二维屏幕空间，绘制顶点（如银河星系数据），操控摄像机旋转模型。
2. 在剪切空间进行3D直线的剪切算法，把顶点连线（如各种三维正多面体）光栅化成wire frame模型。
3. 以多边形来定义三维模型。使用画家算法来光栅化那些多边形。
4. 改为使用深度缓冲。
5. 实现简单的纹理映射，先做屏幕空间插值，然后实现简单的perspective-correct texture mapping。
6. 实现简单的顶点光照，使用顶点颜色插值实现Gouraud shading。
7. 通过顶点法线插值，实现Phong shading。
8. 实现其他贴图技术，如mipmapping（也可试 Summed area table）、bilinear/trilinear filtering、bumpmapping、normal mapping、environment mapping等。

## 我做了什么

创建一个JFrame窗口
 
![](http://blog.jmecn.net/content/images/2017/11/soft-renderer-01.png)

光栅化2d点

![](http://blog.jmecn.net/content/images/2017/11/soft-renderer-08.png)

光栅化2d线段

![](http://blog.jmecn.net/content/images/2017/11/soft-renderer-09.png)

2D直线裁剪

![](http://blog.jmecn.net/content/images/2017/11/soft-renderer-10.png)

光栅化2D三角形

![](http://blog.jmecn.net/content/images/2017/11/soft-renderer-11.png)

创建3D数学库，实现3D空间变换。

![](http://blog.jmecn.net/content/images/2017/11/Test3DWorldMatrix.gif)

创建虚拟摄像机，实现3D观察。

![](http://blog.jmecn.net/content/images/2017/11/3d_cube.gif)

接收用户输入，实现第一人称摄像机控制器。

![](http://blog.jmecn.net/content/images/2017/11/camera_controller.gif)

背面剔除

![](http://blog.jmecn.net/content/images/2017/11/3dview.gif)

3D光栅化

![](http://blog.jmecn.net/content/images/2017/11/3d_opacity_cube.png)

深度缓冲

![](http://blog.jmecn.net/content/images/2017/11/depthTest_2.png)

纹理采样。实现纹理包裹（WarpMode），以及最邻近点（Nearest）和二次线性（Bilinear）滤波。

![](http://blog.jmecn.net/content/images/2017/11/test_warp_mode.png)

Alpha测试

![](http://blog.jmecn.net/content/images/2017/11/alpha_falloff_4.png)

颜色混合

![](http://blog.jmecn.net/content/images/2017/11/test_blend_mode.png)

透视校正插值

![](http://blog.jmecn.net/content/images/2017/11/persp_correct_lerp_3.png)

实现了材质和着色器框架。

![](http://blog.jmecn.net/content/images/2017/12/test_sphere_1.png)

![](http://blog.jmecn.net/content/images/2017/11/unshaded_shader.png)

基于Gouraud Shader的顶点光照

![](http://blog.jmecn.net/content/images/2017/12/vertex_light_2.gif)

实现了Blinn-Phong Shader

![](http://blog.jmecn.net/content/images/2017/12/blinn_phong_shader.png)

看心情继续..