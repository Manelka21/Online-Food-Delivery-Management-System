package com.fooddelivery.repository;

import com.fooddelivery.model.Admin;

import java.util.Optional;

public class AdminRepository extends BaseFileRepository<Admin> {

    @Override
    protected String getFilename() { return "users.txt"; }

    @Override
    protected String getPrefix() { return "ADM"; }

    @Override
    protected int getIdIndex() { return 0; }

    @Override
    protected String getEntityRole() { return "Admin"; }

    @Override
    protected int getRoleIndex() { return 1; }

    @Override
    protected Admin parse(String[] f) {
        if (f.length < 10) return null;
        Admin a = new Admin();
        a.setPersonId(f[0]);
        a.setName(f[2]);
        a.setEmail(f[3]);
        a.setPassword(f[4]);
        a.setPhone(f[5]);
        return a;
    }

    @Override
    protected String[] toFields(Admin a) {
        return new String[]{
            a.getPersonId(), "Admin", a.getName(), a.getEmail(),
            a.getPassword(), a.getPhone(), "", "", "", "true"
        };
    }

    @Override
    protected String getId(Admin a) {
        return a.getPersonId();
    }

    @Override
    protected void setId(Admin a, String id) {
        a.setPersonId(id);
    }

    public Optional<Admin> findByEmailAndPassword(String email, String password) {
        return findBy(a -> a.getEmail().equals(email) && a.getPassword().equals(password));
    }

    public boolean existsByEmail(String email) {
        return findBy(a -> a.getEmail().equals(email)).isPresent();
    }
}
