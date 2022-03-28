package com.onnwencassitto.routingui;

import java.util.UUID;

public class Id {
    protected final UUID id;

    public Id(UUID id) {
        this.id = id;
    }

    public Id() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
