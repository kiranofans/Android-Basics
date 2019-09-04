package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Feeds {
    @SerializedName("timeline_url")
    @Expose
    private String timelineUrl;
    @SerializedName("user_url")
    @Expose
    private String userUrl;
    @SerializedName("current_user_public_url")
    @Expose
    private String currentUserPublicUrl;
    @SerializedName("current_user_url")
    @Expose
    private String currentUserUrl;
    @SerializedName("current_user_actor_url")
    @Expose
    private String currentUserActorUrl;
    @SerializedName("current_user_organization_url")
    @Expose
    private String currentUserOrganizationUrl;
    @SerializedName("current_user_organization_urls")
    @Expose
    private List<String> currentUserOrganizationUrls = null;
    @SerializedName("security_advisories_url")
    @Expose
    private String securityAdvisoriesUrl;
    @SerializedName("_links")
    @Expose
    private Links links;

    public String getTimelineUrl() {
        return timelineUrl;
    }

    public void setTimelineUrl(String timelineUrl) {
        this.timelineUrl = timelineUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getCurrentUserPublicUrl() {
        return currentUserPublicUrl;
    }

    public void setCurrentUserPublicUrl(String currentUserPublicUrl) {
        this.currentUserPublicUrl = currentUserPublicUrl;
    }

    public String getCurrentUserUrl() {
        return currentUserUrl;
    }

    public void setCurrentUserUrl(String currentUserUrl) {
        this.currentUserUrl = currentUserUrl;
    }

    public String getCurrentUserActorUrl() {
        return currentUserActorUrl;
    }

    public void setCurrentUserActorUrl(String currentUserActorUrl) {
        this.currentUserActorUrl = currentUserActorUrl;
    }

    public String getCurrentUserOrganizationUrl() {
        return currentUserOrganizationUrl;
    }

    public void setCurrentUserOrganizationUrl(String currentUserOrganizationUrl) {
        this.currentUserOrganizationUrl = currentUserOrganizationUrl;
    }

    public List<String> getCurrentUserOrganizationUrls() {
        return currentUserOrganizationUrls;
    }

    public void setCurrentUserOrganizationUrls(List<String> currentUserOrganizationUrls) {
        this.currentUserOrganizationUrls = currentUserOrganizationUrls;
    }

    public String getSecurityAdvisoriesUrl() {
        return securityAdvisoriesUrl;
    }

    public void setSecurityAdvisoriesUrl(String securityAdvisoriesUrl) {
        this.securityAdvisoriesUrl = securityAdvisoriesUrl;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
