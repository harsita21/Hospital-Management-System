package com.hospital.model;

public class Doctor extends User {
    private int doctorId; // This is the primary key of the doctors table
    private String name;
    private String specialization;
    private int departmentId;
    private String phone;
    private int experience;
    private String bio;
    private String departmentName;
    
    public Doctor() {}
    
    // Convenience method to get the doctor's table ID (doctors.id)
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    // Inherited getId() returns user_id from users table
    // getDoctorId() returns id from doctors table
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    @Override
    public String toString() {
        return "Doctor{doctorId=" + doctorId + ", name='" + name + "', userId=" + getId() + "}";
    }
}