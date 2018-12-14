package com.jaskiran;

class Person{
    private String name;
    private int age;
    private String gender;
    private boolean disable;

    Person(String name,int age,String gender,boolean disable ){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disable = disable;
            }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }/**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }/**
     * @return the disable
     */
    public boolean isDisable() {
        return disable;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    } 
    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }/**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }/**
     * @param disable the disable to set
     */
    public void setDisable(boolean disable) {
        this.disable = disable;
    }
           

}

