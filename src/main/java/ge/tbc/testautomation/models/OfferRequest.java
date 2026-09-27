package ge.tbc.testautomation.models;

import java.util.List;

public class OfferRequest {

    private List<String> filter;
    private String locale;
    private String segment;
    private int pageIndex;
    private int pageSize;

    public OfferRequest() {
    }

    public OfferRequest(
            List<String> filter,
            String locale,
            String segment,
            int pageIndex,
            int pageSize
    ) {
        this.filter = filter;
        this.locale = locale;
        this.segment = segment;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}