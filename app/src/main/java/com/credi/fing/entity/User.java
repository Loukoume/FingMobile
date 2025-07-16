package com.credi.fing.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    @SerializedName("username")
    private String username;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("clientId")
    private Integer clientId;

    @SerializedName("base64EncodedAuthenticationKey")
    private String base64EncodedAuthenticationKey;

    @SerializedName("authenticated")
    private boolean authenticated;

    @SerializedName("officeId")
    private Integer officeId;

    @SerializedName("officeName")
    private String officeName;

    @SerializedName("roles")
    private List<Role> roles;

    @SerializedName("permissions")
    private List<String> permissions;

    @SerializedName("shouldRenewPassword")
    private boolean shouldRenewPassword;

    @SerializedName("isTwoFactorAuthenticationRequired")
    private boolean isTwoFactorAuthenticationRequired;

    // Constructeur no-args (nécessaire pour Gson)
    public User() { }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getBase64EncodedAuthenticationKey() {
        return base64EncodedAuthenticationKey;
    }

    public void setBase64EncodedAuthenticationKey(String base64EncodedAuthenticationKey) {
        this.base64EncodedAuthenticationKey = base64EncodedAuthenticationKey;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public boolean isShouldRenewPassword() {
        return shouldRenewPassword;
    }

    public void setShouldRenewPassword(boolean shouldRenewPassword) {
        this.shouldRenewPassword = shouldRenewPassword;
    }

    public boolean isTwoFactorAuthenticationRequired() {
        return isTwoFactorAuthenticationRequired;
    }

    public void setTwoFactorAuthenticationRequired(boolean twoFactorAuthenticationRequired) {
        isTwoFactorAuthenticationRequired = twoFactorAuthenticationRequired;
    }

    // Classe imbriquée pour représenter un rôle
    public static class Role {
        @SerializedName("id")
        private Double id;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("disabled")
        private boolean disabled;

        // Constructeur no-args (nécessaire pour Gson)
        public Role() { }

        // Getters & Setters
        public Double getId() {
            return id;
        }

        public void setId(Double id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }
    }
}
