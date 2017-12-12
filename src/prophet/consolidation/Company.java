/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prophet.consolidation;

import java.util.ArrayList;

/**
 *
 * @author Joe Licata
 */
public class Company {

    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String website;
    private String category;
    private ArrayList<Contact> contacts = new ArrayList();
    private Opportunity opportunity;
    private ArrayList<String> opportunityNotes=new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }

    public ArrayList<String> getOpportunityNotes() {
        return opportunityNotes;
    }

    public void setOpportunityNotes(ArrayList<String> opportunityNotes) {
        this.opportunityNotes = opportunityNotes;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
