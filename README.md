# MineSweeper
Big Homework of Java Programming BUAA 2020 Fall.

## TODO List

### 架构相关

### 游戏核心

- [ ] 增加难度评估功能(3bv)

### 图形界面

- [x] 为地雷和旗帜配置图片素材
- [x] 为菜单栏添加动作事件
- [x] 写配置游戏难度的对话框

## 开发进度

- 2020.10.14 周三
  - 上午(Java课+高代)+下午(离散教室+大班会)+晚上(宿舍)：<br>
  重构了`GamePanel`，分解成了`GameBoard`和`GameBoardController`类。<br>
  将其他的View也拆分成了(伪)MVC模型。<br>
  游戏核心类加了统计旗子数/剩余未标记雷数的接口。<br>
  为菜单栏添加了事件，包括新建游戏, 调整难度以及打开智能扫雷AI的弹框。<br>
  添加了调整难度的设置框并测试可用。<br>
  De出来在非正方形地图时行列标写错导致更新View时潜在的bug。<br>
  **finally**: 开发出第一个能玩的版本。支持右键标旗，双键开雷，调整难度，保证第一下不点中雷。程序代码尽可能按照面向对象工程的架构实现(雾)。
- 2020.10.13 周二
  - 凌晨(新主楼2F)：完成了基本的图形界面交互的开发及调试，完成重构了部分代码。<br>
  （目前可以完整地玩初级难度的扫雷了！支持左键右键，已经打开的格子可以双键。游戏结束后提示输赢并且自动重开）<br>
  约 `2:20` 完成了阶段性开发，建立 Git 仓库。<br>
  约 `2:52` 写下了这篇文档。
  - 晚上(新主楼)：添加全局单例类`GameOptions`，利用这个类储存配置选项，包括游戏难度以及是否显示调试信息。<br>
  向吴家焱大佬请教架构优化的问题，争取明天上午能把`GamePanel`类重构一下，分解成`View`和`Controller`。<br>
  将`Controller`和`Model`(`MineSweeperGame`类)连接起来做好接口。
- 2020.10.12 周一
  - 下午(新主楼2F)：构思图形界面的架构，学习MVC模式(似乎失败了)
  - 晚上(新主楼2F)：重构了扫雷网格的图形界面(整体是GridLayout的JPanel，里边每个单元套一个CardLayout的小JPanel，包含一个Button和一个Label)。<br>
  设计扫雷图形界面的控制逻辑与交互响应事件，并多次试玩测试。发现了游戏核心类的一个 Bug 并 de 掉。
- 2020.10.11 周日
  - 下午(新主楼4F)：玩了很多局扫雷，试图发现一些扫雷策略的规律(从而构思扫雷AI)。
  - 晚上(新主楼)：开始学图形界面，创建了菜单栏和游戏核心的扫雷格子。
- 2020.10.10 周六
  - 下午(图书馆)：与 Chenrt 大佬交流，确定了写扫雷。完成了游戏核心部分中单元格和地图类的开发。
  - 晚上(新主楼)：完成了扫雷核心模块的开发，并能够在命令行进行交互。