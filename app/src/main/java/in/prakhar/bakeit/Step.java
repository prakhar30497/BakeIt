package in.prakhar.bakeit;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable{

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;

    public Step(){}

    public Step(int id, String sd, String d, String url){
        this.id = id;
        this.shortDescription = sd;
        this.description = d;
        this.videoURL = url;
    }

    protected Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getStepId(){return id;}
    public String getShortDescription(){return shortDescription;}
    public String getDescription(){return description;}
    public String getVideoUrl(){return videoURL;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
    }
}
