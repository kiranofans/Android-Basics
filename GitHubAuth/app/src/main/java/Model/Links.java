package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Links {
    @SerializedName("timeline")
    @Expose
    private Timeline timeline;
    @SerializedName("user")
    @Expose
    private Users user;
    @SerializedName("current_user_public")
    @Expose
    private CurrentUserPublic currentUserPublic;
    @SerializedName("current_user")
    @Expose
    private CurrentUser currentUser;
    @SerializedName("current_user_actor")
    @Expose
    private CurrentUserActor currentUserActor;
    @SerializedName("current_user_organization")
    @Expose
    private CurrentUserOrg currentUserOrganization;
    @SerializedName("current_user_organizations")
    @Expose
    private List<CurrentUserOrganization_> currentUserOrganizations = null;
    @SerializedName("security_advisories")
    @Expose
    private SecurityAdvisories securityAdvisories;

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public CurrentUserPublic getCurrentUserPublic() {
        return currentUserPublic;
    }

    public void setCurrentUserPublic(CurrentUserPublic currentUserPublic) {
        this.currentUserPublic = currentUserPublic;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public CurrentUserActor getCurrentUserActor() {
        return currentUserActor;
    }

    public void setCurrentUserActor(CurrentUserActor currentUserActor) {
        this.currentUserActor = currentUserActor;
    }

    public CurrentUserOrg getCurrentUserOrganization() {
        return currentUserOrganization;
    }

    public void setCurrentUserOrganization(CurrentUserOrg currentUserOrganization) {
        this.currentUserOrganization = currentUserOrganization;
    }

    public List<CurrentUserOrganization_> getCurrentUserOrganizations() {
        return currentUserOrganizations;
    }

    public void setCurrentUserOrganizations(List<CurrentUserOrganization_> currentUserOrganizations) {
        this.currentUserOrganizations = currentUserOrganizations;
    }

    public SecurityAdvisories getSecurityAdvisories() {
        return securityAdvisories;
    }

    public void setSecurityAdvisories(SecurityAdvisories securityAdvisories) {
        this.securityAdvisories = securityAdvisories;
    }
}
