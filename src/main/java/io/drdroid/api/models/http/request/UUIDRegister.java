package io.drdroid.api.models.http.request;

import java.util.Map;
import java.util.UUID;

public class UUIDRegister {

    private String serviceName;
    private UUID uuid;
    private Map<String, String> resourceKvs;
    private String ip;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Map<String, String> getResourceKvs() {
        return resourceKvs;
    }

    public void setResourceKvs(Map<String, String> resourceKvs) {
        this.resourceKvs = resourceKvs;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
