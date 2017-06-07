package cs143.domain;

import java.io.Serializable;

public class Retiree implements Comparable<Retiree>, Serializable {

    private long ssn;
    private String fullName;
    private double monthlyBenefit;

    public Retiree(long ssn, String fullName, double monthlyBenefit) {
        this.ssn = ssn;
        this.fullName = fullName;
        this.monthlyBenefit = monthlyBenefit;
    }

    public long getSsn() {
        return ssn;
    }

    public String getFullName() {
        return fullName;
    }

    public double getMonthlyBenefit() {
        return monthlyBenefit;
    }

    @Override
    public String toString() {
        return ssn + " : " + fullName + "\n"
                + "monthlyBenefit = $" + monthlyBenefit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.ssn ^ (this.ssn >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Retiree other = (Retiree) obj;
        return this.ssn == other.ssn;
    }

    @Override
    public int compareTo(Retiree o) {
        if (this.ssn < o.ssn) {
            return -1;
        } else if (this.ssn > o.ssn) {
            return 1;
        } else {
            return 0;
        }
    }

}
