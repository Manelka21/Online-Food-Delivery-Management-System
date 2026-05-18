package com.fooddelivery.repository;

import com.fooddelivery.model.Driver;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class DriverRepository {

    private static final String FILE = "users.txt";

    private Driver parse(String[] f) {
        if (f.length < 10 || !f[1].equals("Driver")) return null;
        Driver d = new Driver();
        d.setPersonId(f[0]);
        d.setName(f[2]);
        d.setEmail(f[3]);
        d.setPassword(f[4]);
        d.setPhone(f[5]);
        d.setVehicleNumber(f[7]);
        d.setLicenseNumber(f[8]);
        d.setAvailable(Boolean.parseBoolean(f[9]));
        return d;
    }

    private String[] toFields(Driver d) {
        return new String[]{
            d.getPersonId(), "Driver", d.getName(), d.getEmail(),
            d.getPassword(), d.getPhone(), "",
            d.getVehicleNumber() != null ? d.getVehicleNumber() : "",
            d.getLicenseNumber() != null ? d.getLicenseNumber() : "",
            String.valueOf(d.isAvailable())
        };
    }

    public List<Driver> findAll() {
        List<Driver> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE)) {
            Driver d = parse(f);
            if (d != null) list.add(d);
        }
        return list;
    }

    public Optional<Driver> findById(String id) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f[0].equals(id) && f[1].equals("Driver"))
                return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public Driver save(Driver d) {
        List<String[]> records = FileUtil.readAll(FILE);
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)[0].equals(d.getPersonId()) && records.get(i)[1].equals("Driver")) {
                records.set(i, toFields(d)); found = true; break;
            }
        }
        if (!found) {
            if (d.getPersonId() == null || d.getPersonId().isEmpty())
                d.setPersonId(FileUtil.nextId(FILE, "DRV", 0));
            records.add(toFields(d));
        }
        FileUtil.writeAll(FILE, records);
        return d;
    }

    public void deleteById(String id) {
        List<String[]> records = FileUtil.readAll(FILE);
        records.removeIf(f -> f[0].equals(id) && f.length > 1 && f[1].equals("Driver"));
        FileUtil.writeAll(FILE, records);
    }
}
