package com.jaskiran;
class Party{
    private String room;
    private int capacity;
    private String features;
    private boolean alcoholAllowed;

    Party(String room,int capacity,String features,boolean alcoholAllowed){
        this.room = room;
        this.capacity = capacity;
        this.features = features;
        this.alcoholAllowed =alcoholAllowed;
        
    }
    /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }/**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }/**
     * @return the features
     */
    public String getFeatures() {
        return features;
    }/**
     * @return the alcoholAllowed
     */
    public boolean isAlcoholAllowed() {
        return alcoholAllowed;
    }/**
     * @param room the room to set
     */
    public void setRoom(String room) {
        this.room = room;
    }/**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }/**
     * @param features the features to set
     */
    public void setFeatures(String features) {
        this.features = features;
    }/**
     * @param alcoholAllowed the alcoholAllowed to set
     */
    public void setAlcoholAllowed(boolean alcoholAllowed) {
        this.alcoholAllowed = alcoholAllowed;
    }
}

