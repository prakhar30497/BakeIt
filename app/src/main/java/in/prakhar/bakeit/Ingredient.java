package in.prakhar.bakeit;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient(){

    }

    public Ingredient(float quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in){
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            Ingredient ing = new Ingredient();
            ing.quantity = in.readFloat();
            ing.measure = in.readString();
            ing.ingredient = in.readString();

            return ing;
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public float getQuantity(){
        return quantity;
    }
    public String getMeasure(){
        return measure;
    }
    public String getIngredient(){
        return ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}
