package com.example.covid19tacker;

public class CountryModal {
    private String flag,country,cases,todayscase,death,todaysdeath,recovered,active,critical,conti;

    public CountryModal() {
    }

    public CountryModal(String flag, String country, String cases, String todayscase, String death, String todaysdeath, String recovered, String active, String critical, String conti) {
        this.flag = flag;
        this.country = country;
        this.cases = cases;
        this.todayscase = todayscase;
        this.death = death;
        this.todaysdeath = todaysdeath;
        this.recovered = recovered;
        this.active = active;
        this.critical = critical;
        this.conti = conti;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String gettodayscase() {
        return todayscase;
    }

    public void settodayscase(String todayscase) {
        this.todayscase = todayscase;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String gettodaysdeath() {
        return todaysdeath;
    }

    public void settodaysdeath(String todaysdeath) {
        this.todaysdeath = todaysdeath;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getConti() {
        return conti;
    }

    public void setConti(String conti) {
        this.conti = conti;
    }
}
