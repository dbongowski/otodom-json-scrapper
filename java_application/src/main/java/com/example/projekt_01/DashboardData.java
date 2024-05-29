package com.example.projekt_01;

public class DashboardData {
    private String sales;
    private String orders;
    private String reorderAlerts;
    private String missingItems;

    public DashboardData(String sales, String orders, String reorderAlerts, String missingItems) {
        this.sales = sales;
        this.orders = orders;
        this.reorderAlerts = reorderAlerts;
        this.missingItems = missingItems;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getReorderAlerts() {
        return reorderAlerts;
    }

    public void setReorderAlerts(String reorderAlerts) {
        this.reorderAlerts = reorderAlerts;
    }

    public String getMissingItems() {
        return missingItems;
    }

    public void setMissingItems(String missingItems) {
        this.missingItems = missingItems;
    }
}
