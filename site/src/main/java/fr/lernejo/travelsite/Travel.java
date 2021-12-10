package fr.lernejo.travelsite;

public record Travel(String country, double temperature) {
    @Override
    public int hashCode() {
        int hash = 7;
        for (int i = 0; i < country().length(); i++) {
            hash = hash*31 + country().charAt(i);
        }

        return hash;
    }
}
