package models;

import java.time.LocalDate;

public class CreditCard {

    public enum Provider {
        VISA, MASTERCARD, AMERICAN_EXPRESS
    }

    private String number;
    private String CVC;
    private Provider provider;
    private LocalDate expirationDate;

    public CreditCard(String number, String CVC, Provider provider, LocalDate expirationDate) {
        this.number = number;
        this.CVC = CVC;
        this.provider = provider;
        this.expirationDate = expirationDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCVC() {
        return CVC;
    }

    public void setCVC(String CVC) {
        this.CVC = CVC;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String toString(){
        String endNum = number.substring(number.length()-3, number.length());
        return provider.toString() + " ending with " + endNum;
    }
}
