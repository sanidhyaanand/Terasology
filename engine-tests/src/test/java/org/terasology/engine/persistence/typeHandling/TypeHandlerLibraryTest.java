// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.engine.persistence.typeHandling;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.terasology.engine.persistence.typeHandling.coreTypes.CollectionTypeHandler;
import org.terasology.engine.persistence.typeHandling.coreTypes.EnumTypeHandler;
import org.terasology.engine.persistence.typeHandling.coreTypes.ObjectFieldMapTypeHandler;
import org.terasology.engine.persistence.typeHandling.coreTypes.RuntimeDelegatingTypeHandler;
import org.terasology.engine.persistence.typeHandling.coreTypes.StringMapTypeHandler;
import org.terasology.nui.reflection.MappedContainer;
import org.terasology.nui.reflection.TypeInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeHandlerLibraryTest {
    private final Reflections reflections = new Reflections(getClass().getClassLoader());
    private final TypeHandlerLibrary typeHandlerLibrary = new TypeHandlerLibrary(reflections);

    private enum AnEnum {}

    @MappedContainer
    private static class AMappedContainer {}

    @Test
    public void testEnumHandler() {
        TypeHandler<AnEnum> handler = typeHandlerLibrary.getTypeHandler(AnEnum.class).get();

        assertTrue(handler instanceof EnumTypeHandler);
    }

    @Test
    public void testMappedContainerHandler() {
        TypeHandler<AMappedContainer> handler = typeHandlerLibrary.getTypeHandler(AMappedContainer.class).get();

        assertTrue(handler instanceof ObjectFieldMapTypeHandler);
    }

    @Test
    public void testCollectionHandler() {
        TypeHandler<Set<Integer>> setHandler =
                typeHandlerLibrary.getTypeHandler(new TypeInfo<Set<Integer>>() {}).get();

        assertTrue(setHandler instanceof CollectionTypeHandler);

        TypeHandler<List<Integer>> listHandler =
                typeHandlerLibrary.getTypeHandler(new TypeInfo<List<Integer>>() {}).get();

        assertTrue(listHandler instanceof CollectionTypeHandler);

        TypeHandler<Queue<Integer>> queueHandler =
                typeHandlerLibrary.getTypeHandler(new TypeInfo<Queue<Integer>>() {}).get();

        assertTrue(queueHandler instanceof CollectionTypeHandler);
    }

    @Test
    public void testStringMapHandler() {
        TypeHandler<Map<String, Integer>> handler =
                typeHandlerLibrary.getTypeHandler(new TypeInfo<Map<String, Integer>>() {}).get();

        assertTrue(handler instanceof StringMapTypeHandler);
    }

    @Test
    public void testInvalidTypeHandler() {
        Optional<TypeHandler<Map<Integer, Integer>>> handler =
                typeHandlerLibrary.getTypeHandler(new TypeInfo<Map<Integer, Integer>>() {});

        assertFalse(handler.isPresent());
    }

    @Test
    public void testGetBaseTypeHandler() {
        TypeHandler<Integer> handler = typeHandlerLibrary.getBaseTypeHandler(TypeInfo.of(Integer.class));

        assertTrue(handler instanceof RuntimeDelegatingTypeHandler);
    }
}