package Models;

public class Users {
    private String username,email,avatarUrl;
    public Users(){}

    public Users(String username,String email,String avatarUrl){
        this.username=username;
        this.email=email;
        this.avatarUrl=avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
