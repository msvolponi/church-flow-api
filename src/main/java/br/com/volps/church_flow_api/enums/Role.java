package br.com.volps.church_flow_api.enums;

public enum Role {

    ADMIN("Administrador"),
    MANAGER("Gestor"),
    OPERATOR("Operador");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}