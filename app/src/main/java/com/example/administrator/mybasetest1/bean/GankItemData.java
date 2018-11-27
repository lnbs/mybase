package com.example.administrator.mybasetest1.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import javax.inject.Scope;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:34
 */
public class GankItemData implements Parcelable{
    private boolean error;
    private List<GankItem> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankItem> getResults() {
        return results;
    }

    public void setResults(List<GankItem> results) {
        this.results = results;
    }

    protected GankItemData(Parcel in) {
        error = in.readByte() != 0;
        results = in.createTypedArrayList(GankItem.CREATOR);
    }

    public static final Creator<GankItemData> CREATOR = new Creator<GankItemData>() {
        @Override
        public GankItemData createFromParcel(Parcel in) {
            return new GankItemData(in);
        }

        @Override
        public GankItemData[] newArray(int size) {
            return new GankItemData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (error ? 1 : 0));
        parcel.writeTypedList(results);
    }


    public static class GankItem implements Parcelable{
        private String _id;
        private String createdAt;
        private String desc;
        private List<String> images;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private String used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        protected GankItem(Parcel in) {
            _id = in.readString();
            createdAt = in.readString();
            desc = in.readString();
            images = in.createStringArrayList();
            publishedAt = in.readString();
            source = in.readString();
            type = in.readString();
            url = in.readString();
            used = in.readString();
            who = in.readString();
        }

        public static final Creator<GankItem> CREATOR = new Creator<GankItem>() {
            @Override
            public GankItem createFromParcel(Parcel in) {
                return new GankItem(in);
            }

            @Override
            public GankItem[] newArray(int size) {
                return new GankItem[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(_id);
            parcel.writeString(createdAt);
            parcel.writeString(desc);
            parcel.writeStringList(images);
            parcel.writeString(publishedAt);
            parcel.writeString(source);
            parcel.writeString(type);
            parcel.writeString(url);
            parcel.writeString(used);
            parcel.writeString(who);
        }
    }

}
