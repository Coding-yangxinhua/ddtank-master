package com.pwc.sdc.ddtank.core.impl;

import com.pwc.sdc.ddtank.base.Library;
import com.pwc.sdc.ddtank.base.Mouse;
import com.pwc.sdc.ddtank.base.Point;
import com.pwc.sdc.ddtank.config.DDTankFileConfigProperties;
import com.pwc.sdc.ddtank.config.DMPicConfigProperties;
import com.pwc.sdc.ddtank.core.DDTankCoreTaskProperties;
import com.pwc.sdc.ddtank.core.DDTankPic;
import com.pwc.sdc.ddtank.core.pic.PicFind;
import com.pwc.sdc.ddtank.core.pic.PicFindBuilder;
import com.pwc.sdc.ddtank.type.TowardEnum;
import com.pwc.sdc.ddtank.util.BinaryPicProcess;
import com.pwc.sdc.ddtank.util.ThreadUtils;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDDTankPic implements DDTankPic, Serializable {

    private static final long serialVersionUID = 1L;


    protected Library dm;

    protected DDTankCoreTaskProperties properties;

    private Map<String, PicFind> picFindMap;

    protected Mouse mouse;

    protected BinaryPicProcess binaryPicProcess;



    public AbstractDDTankPic(DDTankCoreTaskProperties properties, Library dm, Mouse mouse) {
        this.properties = properties;
        this.dm = dm;
        this.mouse = mouse;
        this.binaryPicProcess = new BinaryPicProcess(dm);
        Map<String, PicFindBuilder> keyPicFindBuilderMap = DMPicConfigProperties.getKeyPicFindBuilderMap(this.getClass());
        picFindMap = new HashMap<>();
        for (String key : keyPicFindBuilderMap.keySet()) {
            PicFindBuilder picFindBuilder = keyPicFindBuilderMap.get(key);
            picFindMap.put(key, picFindBuilder.build(new File(DDTankFileConfigProperties.getBaseDir(), properties.getPicDir()).getAbsolutePath(), dm));
        }
    }

    /**
     * 判断是否需要进入远征码头
     * @return
     */
    @Override
    public boolean needGoingToWharf() {
        Point point = new Point();
        if (getPicFind("needGoingToWharf").findPic(point)) {
            mouse.moveAndClick(point);
            ThreadUtils.delay(3000, true);
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要创建房间
     * @return
     */
    @Override
    public boolean needCreateRoom() {
        Point point = new Point();
        if (getPicFind("needCreateRoom").findPic(point)) {
            createRoom(point);
            return true;
        }
        return false;
    }

    public void createRoom(Point point) {
        mouse.moveAndClick(point);
        ThreadUtils.delay(1000, true);
    }

    /**
     * 激活游戏窗口
     * @return
     */
    @Override
    public boolean needActiveWindow() {
        if(getPicFind("needActiveWindow").findPic()) {
            Point point = ativeWindowPoint();
            mouse.moveAndClick(point.getX(), point.getY());
            return true;
        }
        return false;
    }

    /**
     * 选择游戏地图
     * @return
     */
    @Override
    public boolean needChooseMap() {
        Point point = new Point();
        if(getPicFind("needChooseMap").findPic(point)) {
            mouse.moveAndClick(point.setOffset(10, 10));
            ThreadUtils.delay(300, true);
        }
        return getPicFind("needChooseMap2").findPic();
    }


    /**
     * 点击开始
     * @return
     */
    @Override
    public boolean needClickStart() {
        Point point = new Point();
        if (getPicFind("needClickStart").findPic(point)) {
            point.setOffset(10, 10);
            mouse.moveAndClick(point);
            return true;
        }
        return false;
    }

    /**
     * 点击准备
     * @return
     */
    @Override
    public boolean needClickPrepare() {
        Point point = new Point();
        if (getPicFind("needClickPrepare").findPic(point)) {
            mouse.moveAndClick(point);
            return true;
        }
        return false;
    }


    /**
     * 点击关闭邮件
     * @return
     */
    @Override
    public boolean needCloseEmail() {
        if (getPicFind("needCloseEmail").findPic()) {
            Point point = mailPoint();
            mouse.moveAndClick(point.getX(), point.getY());
            return true;
        }
        return false;
    }


    /**
     * 点击关闭提示
     * @return
     */
    @Override
    public boolean needCloseTip() {
        boolean result = false;
        if (getPicFind("needCloseTip").findPic()) {
            closeTip();
            result = true;
        }
        Point point = new Point();
        if (getPicFind("needCloseTip2").findPic(point)) {
            point.setOffset(20, 10);
            mouse.moveAndClick(point);
            result = true;
        }
        return result;
    }


    public void closeTip() {
        mouse.moveAndClick(376, 319);
        mouse.moveAndClick(405, 352);
    }

    /**
     * 判断是否进入副本
     * @return
     */
    @Override
    public boolean isEnterLevel() {
        return getPicFind("isEnterLevel").findPic();
    }

    /**
     * 判断是否是我的回合
     * @return
     */
    @Override
    public boolean isMyRound() {
        return getPicFind("isMyRound").findPic();
    }

    /**
     * 判断是否能抽卡
     * @return
     */
    @Override
    public boolean needDraw() {
        List<Point> cardList;
        boolean over = false;
        boolean find = false;
        while ((cardList = getPicFind("needDraw").findPicEx()) != null) {
            find = true;
            if (!over) {
                for (int i = 0; i < 10; i++) {
                    Point point = cardList.get((int) (System.currentTimeMillis() % cardList.size()));
                    mouse.moveAndClick(point);
                    ThreadUtils.delay(100, true);
                }

                if (getPicFind("needDraw2").findPic()) {
                    over = true;
                    if (properties.getIsThirdDraw()) {
                        drawThird();
                    }
                }
                ThreadUtils.delay(1000, true);
            }
            // 全选到背包
            needPutInBag(find);
        }
        return find;
    }

    public void needPutInBag(boolean find) {
        // 如果已经翻过牌或找到了全选进被背包
        if (find || getPicFind("needDraw3").findPic()) {
            Point putInBag = new Point();
            int failTimes = 0;
            // 只要没找到全选按钮，那么就一直找
            while (!getPicFind("needDraw3").findPic(putInBag)) {
                failTimes++;
                ThreadUtils.delay(1000, true);
                if (failTimes > 30) {
                    // 30秒未找到全选按钮，那么就直接返回
                    break;
                }
            }
            mouse.moveAndClick(putInBag);
        }
    }
    public abstract void drawThird();

    /**
     * 理论上来说封装成一个类会见简单点，但考虑到用的地方很少就在这里写了
     *
     * @return
     */
    @Override
    public Point getMyPosition() {
        Point result = binaryPicProcess.findRoundRole(properties.getStaticX1(), properties.getStaticY1(), properties.getStaticX2(), properties.getStaticY2(),
                properties.getColorRole(), 1.0);
        if(result != null) {
            result.setOffset(properties.getMyOffsetX(), properties.getMyOffsetY());
        }
        return result;
    }

    @Override
    public Point getEnemyPosition() {
        Point result = new Point();
        String[] colors = properties.getColorEnemy().split("\\|");
        for (String color : colors) {
            if (dm.findColor(properties.getStaticX1(), properties.getStaticY1(), properties.getStaticX2(), properties.getStaticY2(),
                    color, 1, properties.getEnemyFindMode(), result)) {
                switch (properties.getEnemyFindMode()) {
                    case 0:
                    case 5:
                        // 找的是左上角
                        result.setOffset(2, 2);
                        break;
                    case 1:
                    case 7:
                        // 找的是左下角
                        result.setOffset(2, -2);
                        break;
                    case 2:
                    case 6:
                        // 找的是右上角
                        result.setOffset(-2, 2);
                        break;
                    case 3:
                    case 8:
                        // 找的是右下角
                        result.setOffset(-2, -2);
                        break;
                }
                result.setOffset(properties.getEnemyOffsetX(), properties.getEnemyOffsetY());
                return result;
            }
        }
        return null;
    }


    @Override
    public TowardEnum getToward() {
        List<Point> linePoint = binaryPicProcess.findLine(10, 503, 90, 589, "ff0000-402020|e0b040-202010", 1);
        if (linePoint == null) {
            return TowardEnum.UNKNOWN;
        } else {
            int center = (90 - 10) / 2 + 10;
            int left = 0;
            int right = 0;
            for (Point point : linePoint) {
                if (point.getX() > center) {
                    right++;
                } else if (point.getX() < center) {
                    left++;
                }
            }
            if (left > right) {
                return TowardEnum.LEFT;
            } else if (left < right) {
                return TowardEnum.RIGHT;
            } else {
                return TowardEnum.BOTH;
            }
        }
    }

    @Override
    public double getWind() {
        String windStr = dm.ocr(461, 20, 536, 42, "000000-000000|ff0000-000000", 1);
        double wind = Double.parseDouble(windStr);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 1000) {
            if (dm.findColor(524, 25, 537, 39, "eaeaea|848484", 1, 0, null)) {
                return -wind;
            } else if (dm.findColor(462, 26, 474, 48, "eaeaea|848484", 1, 0, null)) {
                return wind;
            }
        }
        return 0;
    }

    public Point ativeWindowPoint() {
        return new Point(84, 578);
    }

    public abstract Point mailPoint();

    protected PicFind getPicFind(String key) {
        return picFindMap.get(key);
    }
}