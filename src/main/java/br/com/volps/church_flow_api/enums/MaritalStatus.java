package br.com.volps.church_flow_api.enums;

public enum MaritalStatus {
    SINGLE("Solteiro(a)"),
    MARRIED("Casado(a)"),
    DIVORCED("Divorciado(a)"),
    WIDOWED("Viúvo(a)");

    private final String label;

    MaritalStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
