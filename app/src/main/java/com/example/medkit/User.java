package com.example.medkit;

import android.graphics.Bitmap;

public class User {

    private String email, password, name, mobile, address, birthday, biologicalSex, bloodType, allergies, history, chronicIllnesses;
    private String emergencyName, emergencyRelation, emergencyMobile;
    private int height, weight;
    private Bitmap profileImage, vaccinationImage, healthCareImage, xRayImage;

    public User(String name, String mobile, String address, String birthday, String biologicalSex, String bloodType, String allergies, String history, String chronicIllnesses, String emergencyName, String emergencyRelation, String emergencyMobile, int height, int weight, Bitmap profileImage) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.birthday = birthday;
        this.biologicalSex = biologicalSex;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.history = history;
        this.chronicIllnesses = chronicIllnesses;
        this.emergencyName = emergencyName;
        this.emergencyRelation = emergencyRelation;
        this.emergencyMobile = emergencyMobile;
        this.height = height;
        this.weight = weight;
        this.profileImage = profileImage;
    }

    public Bitmap getVaccinationImage() {
        return vaccinationImage;
    }

    public void setVaccinationImage(Bitmap vaccinationImage) {
        this.vaccinationImage = vaccinationImage;
    }

    public Bitmap getHealthCareImage() {
        return healthCareImage;
    }

    public void setHealthCareImage(Bitmap healthCareImage) {
        this.healthCareImage = healthCareImage;
    }

    public Bitmap getXRayImage() {
        return xRayImage;
    }

    public void setXRayImage(Bitmap xRayImage) {
        this.xRayImage = xRayImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBiologicalSex() {
        return biologicalSex;
    }

    public void setBiologicalSex(String biologicalSex) {
        this.biologicalSex = biologicalSex;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getChronicIllnesses() {
        return chronicIllnesses;
    }

    public void setChronicIllnesses(String chronicIllness) {
        this.chronicIllnesses = chronicIllness;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyRelation() {
        return emergencyRelation;
    }

    public void setEmergencyRelation(String emergencyRelation) {
        this.emergencyRelation = emergencyRelation;
    }

    public String getEmergencyMobile() {
        return emergencyMobile;
    }

    public void setEmergencyMobile(String emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }
}
