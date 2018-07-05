# debain 笔记本屏幕亮度

## 默认情况下屏幕亮度调整

设置屏幕亮度的文件在 `/sys/class/backlight/acpi_video0` 目录下，设置亮度的文件为 `brightness`, 最大亮度的的值记录在 `max_brightness`,

笔记本默认的功能键调整会修改 `/sys/class/backlight/acpi_video0/brightness` 的值

## intel 显卡的屏幕调整

设置屏幕亮度的文件在 `/sys/class/backlight/intel_backlight` 目录下，同样有 `brightness`,  `max_brightness`，修改 brightness 即可调整亮度

### intel 显卡屏幕亮度实现快捷键修改

由于快捷键调整屏幕亮度只会修改 `/sys/class/backlight/acpi_video0/brightness`，所以对 intel 显卡没有作用

在百度贴吧找到一个实现方案：

通过在 /udev/rules 添加一个 rule 文件，捕捉屏幕亮度修改的事件，然后在此事件上加上执行 修改 intel_backlight 的动作

具体实现：

新建修改 brightness 文件的 shell，保存在 `/usr/local/bin/writeintelbacklight.sh`

文件内容
```
#!/bin/bash

intelPath='/sys/class/backlight/intel_backlight'
acpiPath='/sys/class/backlight/acpi_video0'

intelMin=100000
intelMax=`cat $intelPath/max_brightness`
acpiMax=`cat $acpiPath/max_brightness`

scale=$[( $intelMax - $intelMin ) / $acpiMax]

acpiV=`cat $acpiPath/brightness`
intelV=`cat $intelPath/brightness`

newIntelV=$[$scale * $acpiV + $intelMin]

echo $scale
echo $newIntelV
echo $intelV

if [ $newIntelV -ne $intelV ]
then
    echo $newIntelV > $intelPath/brightness
fi

exit 0
```

新建 rule 文件,保存在 /etc/udev/rules.d/99-writeintelbacklight.rules

文件内容：
```
ACTION=="change", SUBSYSTEM=="backlight", RUN+="/usr/local/bin/writeintelbacklight.sh"
```
