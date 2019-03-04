package cn.wolfcode.p2p.base.query;

import org.springframework.util.StringUtils;

public class QueryObject {
    private int currentPage = 1;
    private int pageSize = 10;

    private String orderBy;
    //排序类型asc/desc
    private String orderType;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return StringUtils.hasLength(orderBy) ? orderBy : null;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderType() {
        return StringUtils.hasLength(orderType) ? orderType : null;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getStart(){
        return (currentPage - 1) * pageSize;
    }
}
