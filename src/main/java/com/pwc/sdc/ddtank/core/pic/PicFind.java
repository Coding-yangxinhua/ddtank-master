package com.pwc.sdc.ddtank.core.pic;

import com.pwc.sdc.ddtank.base.Point;

import java.util.List;

public interface PicFind {

    boolean findPic();

    boolean findPic(Point point);

    List<Point> findPicEx();
}
