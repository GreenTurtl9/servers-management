package io.getarrays.server.enumeration;

public enum Status {

    SERVER_UP("SERVER_UP"),
    SERVER_DOWN("SERVER_DOWN");

    private final String serverStatus;

    Status(String status) {
        this.serverStatus = status;
    }

    public String getServerStatus() {
        return this.serverStatus;
    }
}
