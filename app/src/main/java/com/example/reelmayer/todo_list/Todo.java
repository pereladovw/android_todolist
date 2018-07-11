package com.example.reelmayer.todo_list;

import android.os.Parcel;
import android.os.Parcelable;

class Todo  implements Parcelable {

    public Integer id;
    public String text;
    public Boolean isCompleted = false;

    public Integer project_id;

    public Todo(String text) {
        this.text = text;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Todo(String text, Integer project_id) {
        this.text = text;
        this.project_id = project_id;
    }

    public Todo(Integer id, String text, Integer project_id, Boolean isCompleted) {
        this.id = id;
        this.text = text;
        this.project_id = project_id;
        this.isCompleted = isCompleted;
    }

    public Todo(Parcel parcel) {
        text = parcel.readString();
        project_id = parcel.readInt();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeInt(project_id);
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {

        public Todo createFromParcel(Parcel in) {

            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int i) {
            return new Todo[0];
        }


    };
}
