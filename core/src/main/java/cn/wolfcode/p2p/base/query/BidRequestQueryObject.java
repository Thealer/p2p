package cn.wolfcode.p2p.base.query;

public class BidRequestQueryObject extends QueryObject{
    private int bidRequestState = -1;

    private int[] bidRequestStates;

    private int bidRequestType = 0;

    public int getBidRequestType() {
        return bidRequestType;
    }

    public void setBidRequestType(int bidRequestType) {
        this.bidRequestType = bidRequestType;
    }

    public int[] getBidRequestStates() {
        return bidRequestStates;
    }

    public void setBidRequestStates(int[] bidRequestStates) {
        this.bidRequestStates = bidRequestStates;
    }

    public int getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(int bidRequestState) {
        this.bidRequestState = bidRequestState;
    }
}
