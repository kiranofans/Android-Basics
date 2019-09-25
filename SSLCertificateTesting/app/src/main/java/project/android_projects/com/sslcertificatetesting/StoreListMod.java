package project.android_projects.com.sslcertificatetesting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreListMod {
    @SerializedName("records")
    private List<Store> mStoreList = null;

    public List<Store> getStoreList() {
        return mStoreList;
    }

    public void setmStoreList(List<Store> mStoreList) {
        this.mStoreList = mStoreList;
    }

    class Store {
        @SerializedName("gid")
        private int mGroupId = 0;
        @SerializedName("cids")
        private List<Integer> mCategoryIds = null;
        @SerializedName("nm")
        private String mName = "";
        @SerializedName("lon")
        private Float mLongitude=0f;
        @SerializedName("lat")
        private Float mLatitude=0f;
        @SerializedName("rnk")
        private Float mRank = 0f;
        @SerializedName("clv")
        private int mCostLevel = 0;
        @SerializedName("mt")
        private String mMeta = "";
        @SerializedName("logo")
        private String mLogo = "";
        @SerializedName("adr")
        private String mAddress = "";
        @SerializedName("scnt")
        private int mSoldCount = 0;
        @SerializedName("df")
        private int mDisplayFormat = 0;

        public int getmGroupId() {
            return mGroupId;
        }

        public void setGroupId(int mGroupId) {
            this.mGroupId = mGroupId;
        }

        public List<Integer> getmCategoryIds() {
            return mCategoryIds;
        }

        public void setmCategoryIds(List<Integer> mCategoryIds) {
            this.mCategoryIds = mCategoryIds;
        }

        public String getStoreName() {
            return mName;
        }

        public void setStoreName(String mName) {
            this.mName = mName;
        }

        public Float getmLongitude() {
            return mLongitude;
        }

        public void setmLongitude(Float mLongitude) {
            this.mLongitude = mLongitude;
        }

        public Float getmLatitude() {
            return mLatitude;
        }

        public void setmLatitude(Float mLatitude) {
            this.mLatitude = mLatitude;
        }

        public Float getmRank() {
            return mRank;
        }

        public void setmRank(Float mRank) {
            this.mRank = mRank;
        }

        public int getmCostLevel() {
            return mCostLevel;
        }

        public void setmCostLevel(int mCostLevel) {
            this.mCostLevel = mCostLevel;
        }

        public String getmMeta() {
            return mMeta;
        }

        public void setmMeta(String mMeta) {
            this.mMeta = mMeta;
        }

        public String getLogo() {
            return mLogo;
        }

        public void setmLogo(String mLogo) {
            this.mLogo = mLogo;
        }

        public String getmAddress() {
            return mAddress;
        }

        public void setmAddress(String mAddress) {
            this.mAddress = mAddress;
        }

        public int getmSoldCount() {
            return mSoldCount;
        }

        public void setmSoldCount(int mSoldCount) {
            this.mSoldCount = mSoldCount;
        }

        public int getmDisplayFormat() {
            return mDisplayFormat;
        }

        public void setmDisplayFormat(int mDisplayFormat) {
            this.mDisplayFormat = mDisplayFormat;
        }
    }
}
