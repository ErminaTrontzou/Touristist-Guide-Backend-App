package gr.thegoodsideofe1.tourguide.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_collections")
public class UserCollection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_public")
    private boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public UserCollection(int id, String name, String description, boolean isPublic, User userID){
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.user = userID;
    }

    public UserCollection() {
    }

    public int getId(){
        return this.id;
    }
    public void setId(int newID){
        this.id = newID;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public boolean getIsPublic(){
        return this.isPublic;
    }
    public void setIsPublic(boolean newIsPublic){
        this.isPublic = newIsPublic;
    }

    public User getUser_id() {
        return this.user;
    }
    public void setUser_id(User user_id) {
        this.user = user_id;
    }
}
