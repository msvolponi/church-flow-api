package br.com.volps.church_flow_api.dto;

import org.springframework.hateoas.RepresentationModel;

public class LoginResponse extends RepresentationModel<LoginResponse> {
        private String token;
        private String type;

        public String getToken() {
                return token;
        }

        public void setToken(String token) {
                this.token = token;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }
}