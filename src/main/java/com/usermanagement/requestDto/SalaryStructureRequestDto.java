package com.usermanagement.requestDto;

public class SalaryStructureRequestDto {
    private Double basic;
    private Double hra;
    private Double da;
    private Double otherAllowances;
    private Double pfDeduction;
    private Double esiDeduction;

    public SalaryStructureRequestDto() {
    }

    public Double getBasic() {
        return basic;
    }

    public void setBasic(Double basic) {
        this.basic = basic;
    }

    public Double getHra() {
        return hra;
    }

    public void setHra(Double hra) {
        this.hra = hra;
    }

    public Double getDa() {
        return da;
    }

    public void setDa(Double da) {
        this.da = da;
    }

    public Double getOtherAllowances() {
        return otherAllowances;
    }

    public void setOtherAllowances(Double otherAllowances) {
        this.otherAllowances = otherAllowances;
    }

    public Double getPfDeduction() {
        return pfDeduction;
    }

    public void setPfDeduction(Double pfDeduction) {
        this.pfDeduction = pfDeduction;
    }

    public Double getEsiDeduction() {
        return esiDeduction;
    }

    public void setEsiDeduction(Double esiDeduction) {
        this.esiDeduction = esiDeduction;
    }
}
