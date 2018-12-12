package com.jaskiran;
class Bus{
   private String destination;
 private  int capacity;
 private  String feature;
 private  String accessible;

   Bus (String destination,int capacity,String feature,String accessible){
       this.destination= destination;
       this.capacity = capacity;
       this.feature = feature;
       this.accessible = accessible;
   }
   /**
    * @return the destination
    */
   public String getDestination() {
       return destination;
   }/**
    * @return the capacity
    */
   public int getCapacity() {
       return capacity;
   }/**
    * @return the feature
    */
   public String getFeature() {
       return feature;
   }/**
    * @return the accessible
    */
   public String getAccessible() {
       return accessible;
   }/**
    * @param destination the destination to set
    */
   public void setDestination(String destination) {
       this.destination = destination;
   }/**
    * @param capacity the capacity to set
    */
   public void setCapacity(int capacity) {
       this.capacity = capacity;
   }/**
    * @param feature the feature to set
    */
   public void setFeature(String feature) {
       this.feature = feature;
   }/**
    * @param accessible the accessible to set
    */
   public void setAccessible(String accessible) {
       this.accessible = accessible;
   }
    
}
    
    
    