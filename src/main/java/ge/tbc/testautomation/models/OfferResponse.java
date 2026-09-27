package ge.tbc.testautomation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferResponse {

    private PagingDetails pagingDetails;
    private List<Offer> list;

    public OfferResponse() {
    }

    public PagingDetails getPagingDetails() {
        return pagingDetails;
    }

    public void setPagingDetails(PagingDetails pagingDetails) {
        this.pagingDetails = pagingDetails;
    }

    public List<Offer> getList() {
        return list;
    }

    public void setList(List<Offer> list) {
        this.list = list;
    }
}