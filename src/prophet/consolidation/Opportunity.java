/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prophet.consolidation;

/**
 *
 * @author Joe Licata
 */
public class Opportunity {

    private String description;
    private String salesStage;
    private String salesPriority;
    private String salesRevenue;
    private String lastUpdated;
    private String company;
    private String salesType;
    private String serviceType;
    private String recurRevenue;

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRecurRevenue() {
        return recurRevenue;
    }

    public void setRecurRevenue(String recurRevenue) {
        this.recurRevenue = recurRevenue;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public String getSalesStage() {
        return salesStage;
    }

    public String getSalesPriority() {
        return salesPriority;
    }

    public String getSalesRevenue() {
        return salesRevenue;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSalesStage(String salesStage) {
        this.salesStage = salesStage;
    }

    public void setSalesPriority(String salesPriority) {
        this.salesPriority = salesPriority;
    }

    public void setSalesRevenue(String salesRevenue) {
        this.salesRevenue = salesRevenue;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
