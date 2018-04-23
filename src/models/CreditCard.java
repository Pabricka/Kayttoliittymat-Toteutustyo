package models;

import java.time.YearMonth;

public class CreditCard {

    public enum Provider {
        VISA("Visa"),
        MASTERCARD("MasterCard"),
        AMEX("American Express");

        private final String name;

        private Provider(String s){
            name = s;
        }
        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

    private String number;
    private String cvv;
    private Provider provider;
    private YearMonth expirationDate;

    public CreditCard(String number, String cvv, Provider provider, YearMonth expirationDate) {
        this.number = number;
        this.cvv = cvv;
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
        return cvv;
    }

    public void setCVC(String CVC) {
        this.cvv = CVC;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String toString(){
        String endNum = number.substring(number.length()-3, number.length());
        return provider.toString() + " ending with " + endNum;
    }
}
