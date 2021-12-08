package fr.lernejo.travelsite;

import java.util.Objects;

public record Travel(String country, double temperature) {
    @Override
    public int hashCode() {
        int hash = 7;
        for (int i = 0; i < country().length(); i++) {
            hash = hash*31 + country().charAt(i);
        }

        return hash;
    }

    /*
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Travel other = (Travel) obj;
        if (!Objects.equals(country(), other.country()))
            return false;
        if (hashCode() != other.hashCode())
            return false;
        return true;
    }
    */
}
