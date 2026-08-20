package br.com.volps.church_flow_api.enums;

public enum FollowUpReason {
    HOSPITALIZATION("Hospitalização"),
    ILLNESS("Doença"),
    BIRTH("Nascimento"),
    MARRIAGE("Casamento"),
    MOURNING("Luto"),
    PRAYER_REQUEST("Pedido de oração"),
    FAMILY("Família"),
    FOLLOW_UP("Acompanhamento"),
    CELEBRATION("Celebração"),
    OTHER("Outro");

    private final String label;

    FollowUpReason(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
