package br.com.volps.church_flow_api.enums;

public enum MemberType {

    VISITOR("Visitante"),
    MEMBER("Membro");

    private final String label;

    MemberType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
