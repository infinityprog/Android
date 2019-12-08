package com.example.projetandroid.Entity;

public class Automate {

    private int id;
    private String name;
    private String description;
    private String ip;
    private int slot;
    private int rack;
    private int idUser;

    public Automate(int id, String name, String description, String ip, int slot, int rack, int idUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ip = ip;
        this.slot = slot;
        this.rack = rack;
        this.idUser = idUser;
    }

    public Automate(String name, String description, String ip, int slot, int rack, int idUser) {
        this.name = name;
        this.description = description;
        this.ip = ip;
        this.slot = slot;
        this.rack = rack;
        this.idUser = idUser;
    }

    public Automate(String name, String ip, int slot, int rack, int idUser) {
        this.name = name;
        this.ip = ip;
        this.slot = slot;
        this.rack = rack;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getRack() {
        return rack;
    }

    public void setRack(int rack) {
        this.rack = rack;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return name + " " + description + " " + ip + " r: " + rack + " s:" + slot;
    }
}
