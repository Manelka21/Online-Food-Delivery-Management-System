package com.fooddelivery.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseFileRepository<T> {

    protected abstract String getFilename();
    protected abstract String getPrefix();
    protected abstract int getIdIndex();
    protected abstract String getEntityRole();
    protected abstract int getRoleIndex();

    protected abstract T parse(String[] fields);
    protected abstract String[] toFields(T entity);
    protected abstract String getId(T entity);
    protected abstract void setId(T entity, String id);

    private final ConcurrentHashMap<String, T> cache = new ConcurrentHashMap<>();
    private boolean isCacheLoaded = false;

    protected synchronized void loadCacheIfNeeded() {
        if (!isCacheLoaded) {
            cache.clear();
            for (String[] f : FileUtil.readAll(getFilename())) {
                if (getEntityRole() == null || (f.length > getRoleIndex() && f[getRoleIndex()].equals(getEntityRole()))) {
                    T entity = parse(f);
                    if (entity != null) {
                        cache.put(getId(entity), entity);
                    }
                }
            }
            isCacheLoaded = true;
        }
    }

    protected synchronized void flushCacheToDisk() {
        if (getEntityRole() == null) {
            List<String[]> records = cache.values().stream()
                    .map(this::toFields)
                    .collect(Collectors.toList());
            FileUtil.writeAll(getFilename(), records);
        } else {
            List<String[]> allRecords = FileUtil.readAll(getFilename());
            List<String[]> updatedRecords = new ArrayList<>();

            for (String[] r : allRecords) {
                if (r.length <= getRoleIndex() || !r[getRoleIndex()].equals(getEntityRole())) {
                    updatedRecords.add(r);
                }
            }

            for (T entity : cache.values()) {
                updatedRecords.add(toFields(entity));
            }
            
            FileUtil.writeAll(getFilename(), updatedRecords);
        }
    }

    public List<T> findAll() {
        loadCacheIfNeeded();
        return new ArrayList<>(cache.values());
    }

    public Optional<T> findById(String id) {
        loadCacheIfNeeded();
        return Optional.ofNullable(cache.get(id));
    }

    public T save(T entity) {
        loadCacheIfNeeded();
        
        String id = getId(entity);
        if (id == null || id.isEmpty()) {
            id = FileUtil.nextId(getFilename(), getPrefix(), getIdIndex());
            setId(entity, id);
        }
        
        cache.put(id, entity);
        flushCacheToDisk();
        return entity;
    }

    public void deleteById(String id) {
        loadCacheIfNeeded();
        if (cache.remove(id) != null) {
            flushCacheToDisk();
        }
    }

    protected Optional<T> findBy(Function<T, Boolean> predicate) {
        loadCacheIfNeeded();
        return cache.values().stream()
                .filter(predicate::apply)
                .findFirst();
    }
}
