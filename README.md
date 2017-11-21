基于 Java 的软件光栅化渲染器

==

如何开始用 C++ 写一个光栅化渲染器？ - Milo Yip的回答 - 知乎
https://www.zhihu.com/question/24786878/answer/29039253

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