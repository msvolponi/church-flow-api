package br.com.volps.church_flow_api.enums;

public enum FollowUpType {
    VISIT("Visita"),
    PHONE_CALL("Ligação"),
    MESSAGE("Mensagem"),
    COUNSELING("Aconselhamento"),
    PRAYER("Oração"),
    OTHER("Outro");

    private final String label;

    FollowUpType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
