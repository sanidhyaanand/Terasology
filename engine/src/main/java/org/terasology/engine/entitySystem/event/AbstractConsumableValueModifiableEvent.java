// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.engine.entitySystem.event;

import org.terasology.engine.network.NoReplicate;

public abstract class AbstractConsumableValueModifiableEvent extends AbstractValueModifiableEvent implements ConsumableEvent {
    @NoReplicate
    protected boolean consumed;

    protected AbstractConsumableValueModifiableEvent(float baseValue) {
        super(baseValue);
    }

    @Override
    public void consume() {
        consumed = true;
    }

    @Override
    public boolean isConsumed() {
        return consumed;
    }
}