package cn.wolfcode.p2p.business.query;

import cn.wolfcode.p2p.base.query.QueryObject;
//债权转让查询对象
public class CreditTransferQueryObject extends QueryObject{
    //当前转让人
    private Long currentUserId;

    //债权标状态
    private int bidRequestState = -1;

    public int getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(int bidRequestState) {
        this.bidRequestState = bidRequestState;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }
}
