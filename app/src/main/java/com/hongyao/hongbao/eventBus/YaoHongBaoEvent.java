package com.hongyao.hongbao.eventBus;

/**
 * Created by liaolan on 16/3/31.
 */
public class YaoHongBaoEvent {
    //1-马上去摇，2-已摇到，3-已抢光
    public static final int STATUS_GOTTEN = 2;
    public static final int STATUS_EMPTY = 3;
    public String hbId;
    public String money;
    public int status;
}
