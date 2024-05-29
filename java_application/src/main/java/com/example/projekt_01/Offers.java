package com.example.projekt_01;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;

import java.net.URL;

public class Offers
{
    private String image;
    private String title;
    private String address;
    private Integer cost;
    private Integer area;

    private Integer rooms;

    private Hyperlink url;

    public Offers(String image, String title, String address,  Integer cost, Integer area, Integer rooms, Hyperlink url)
    {
        this.image = image;
        this.title = title;
        this.address = address;
        this.cost = cost;
        this.area = area;
        this.rooms = rooms;
        this.url = url;
    }

    public String getImage()
    {
        return image;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAddress()
    {
        return address;
    }

    public Integer getCost()
    {
        return cost;
    }

    public Integer getArea()
    {
        return area;
    }

    public Integer getRooms()
    {
        return rooms;
    }

    public Hyperlink getUrl()
    {
        return url;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCost(Integer cost)
    {
        this.cost = cost;
    }

    public void setArea(Integer area)
    {
        this.area = area;
    }

    public void setRooms(Integer rooms)
    {
        this.rooms = rooms;
    }

    public void Hyperlink(Hyperlink url)
    {
        this.url = url;
    }


}
