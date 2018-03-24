1. # TRP工具包

   本Mod是由TRP团队成员制作，主要是为了简化红石项目中的Debug流程，参与制作的成员有@[Erioifpud](https://github.com/Erioifpud/)，@[Numeration](https://github.com/Numeration)和@hhyx，**目前只支持单机模式**。

   **请务必阅读本使用手册后再使用！**

   ​

   ## 方块介绍

   1. 侦测器

      ![](https://github.com/The-Red-Pixel/TRP-Toolkit/blob/dev/pics/monitor.png?raw=true)

      **侦测器方块**用于监测**自身下方方块**的一些属性，比如说**比较器**，**小键盘方块**，红石线，任意容器，音乐盒，光感器，压力踏板的能量和液体类方块的等级。

      玩家能通过**鼠标右键**对**侦测器方块**进行一些操作，比如说：

      - 当玩家手持**侦测器方块**操作时会打开一个折线图，该图表能展示出侦测器方块记录的最新的20个数据（20是默认值，能在Mod设置界面里修改）
      - 当玩家手持其他方块操作时，侦测器上方会显示一些文字，此时侦测器会被设为Debug模式，当监测的数值发生改变时，侦测器会将新数值和时间（可能有些偏差）作为**提示窗口**显示在玩家屏幕的右上角，文字对应了**提示窗口**的颜色（多个侦测器使用同一种颜色时会导致提示窗口互相覆盖）。

   2. 小键盘

      ![](https://github.com/The-Red-Pixel/TRP-Toolkit/blob/dev/pics/keypad.png?raw=true)**小键盘方块**用于输出指定强度的信号，玩家通过右键方块的彩色区域进行设定，**信号必须通过比较器接收**，方块正面的正方形区域分别表示数字1~9，下方的淡黄色区域表示数字0，右上方和右下方分别表示撤销一位和清零。

      当小键盘上方存在侦测器时，能量**能够并且只会**被侦测器获取并显示，否则小键盘会将玩家的操作通过屏幕右上角的提示窗口进行显示。

   3. 容器箱

      ![](https://github.com/The-Red-Pixel/TRP-Toolkit/blob/dev/pics/box.png?raw=true)

      ![](https://github.com/The-Red-Pixel/TRP-Toolkit/blob/dev/pics/box_gui.png?raw=true)

      **容器箱**用于生成带有指定强度的容器，玩家右键此方块能打开一个界面，界面内第一行的输入框为目标信号强度；第二行有3个物品选择器，分别用于选择填充到容器里的物品，将鼠标移至白色区域内通过鼠标滚轮进行物品的选取；第三行选择目标容器；最后**点击完成或者使用回车**即可生成指定容器。

   4. 区块加载器

      ![](https://github.com/The-Red-Pixel/TRP-Toolkit/blob/dev/pics/chunk.png?raw=true)

      **区块加载器**用于强制加载区块，默认加载范围是自身所在区块的周围一圈（暂时不支持修改范围），在被放置时将区块加入加载列表，在被摧毁时将区块移出加载列表，**玩家鼠标指向该方块**时能显示该方块负责的加载范围。

   5. 基于脉冲的侦测器

      ![](https://github.com/The-Red-Pixel/TRP-Toolkit/blob/dev/pics/pulse_base_monitor.png?raw=true)

      作用和**侦测器方块**一样，但是自身不会一直监测数值变化，当玩家给予该方块信号时会进行信号的检测，比如信号从0->1时或从1->0时都能触发。