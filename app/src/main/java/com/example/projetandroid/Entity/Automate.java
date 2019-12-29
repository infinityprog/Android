package com.example.projetandroid.Entity;

public class Automate {

    private int id;
    private String description;
    private String ip;
    private int slot;
    private int rack;
    private int idUser;

    public Automate(int id, String description, String ip, int slot, int rack, int idUser) {
        this.id = id;
        this.description = description;
        this.ip = ip;
        this.slot = slot;
        this.rack = rack;
        this.idUser = idUser;
    }

    public Automate(String description, String ip, int slot, int rack, int idUser) {
        this.description = description;
        this.ip = ip;
        this.slot = slot;
        this.rack = rack;
        this.idUser = idUser;
    }

    public Automate( String ip, int slot, int rack, int idUser) {
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
        return description + " " + ip + " r: " + rack + " s:" + slot;
    }
}
