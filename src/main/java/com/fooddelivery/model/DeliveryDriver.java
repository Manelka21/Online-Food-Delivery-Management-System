package com.fooddelivery.model;

public class DeliveryDriver {

    // Encapsulated fields
    private String driverId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String vehicleType;   // BIKE, SCOOTER, CAR
    private String licenseNumber;
    private String status;        // ACTIVE, INACTIVE, ON_DELIVERY
    private String registeredDate;

    //Constructors
    public DeliveryDriver() {}

    public DeliveryDriver(String driverId, String firstName, String lastName,
                          String email, String password, String phone,
                          String vehicleType, String licenseNumber,
                          String status, String registeredDate) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber;
        this.status = status;
        this.registeredDate = registeredDate;
    }

    //Getters & Setters (Encapsulation)

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRegisteredDate() { return registeredDate; }
    public void setRegisteredDate(String registeredDate) { this.registeredDate = registeredDate; }

    //Utility

    public String getFullName() {
        return firstName + " " + lastName;
    }

    //Serialize driver to a pipe-delimited string for file storage//
    public String toFileString() {
        return driverId + "|" + firstName + "|" + lastName + "|" + email + "|" +
               password + "|" + phone + "|" + vehicleType + "|" +
               licenseNumber + "|" + status + "|" + registeredDate;
    }

    //Deserialize driver from a pipe-delimited file line//
    public static DeliveryDriver fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 10) return null;
        return new DeliveryDriver(
            parts[0], parts[1], parts[2], parts[3], parts[4],
            parts[5], parts[6], parts[7], parts[8], parts[9]
        );
    }

    @Override
    public String toString() {
        return "DeliveryDriver{id='" + driverId + "', name='" + getFullName() +
               "', email='" + email + "', status='" + status + "'}";
    }
}
