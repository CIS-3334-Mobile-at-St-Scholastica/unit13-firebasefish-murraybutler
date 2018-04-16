package css.cis3334.fishlocatorfirebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cssuser on 4/20/2017.
 */

public class FishFirebaseData {

    public static final String FishDataTag = "Fish Data";
    DatabaseReference fishBase;

    public DatabaseReference open()  {
        // Get an instance of the database and a reference to the fish data in it
        DatabaseReference fishBase = FirebaseDatabase.getInstance().getReference(FishDataTag);
        return fishBase;
    }

    public void close() {

    }

    public Fish createFish( String species, String weightInOz, String dateCaught) {           //Added String rating as a parameter
        // ---- Get a new database key for the vote
        String key = fishBase.child(FishDataTag).push().getKey();
        // ---- set up the fish object
        Fish newFish = new Fish(key,species,weightInOz,dateCaught);
        // ---- write the vote to Firebase
        fishBase.child(key).setValue(newFish);
        return newFish;
    }

    public Fish createFish( String species, String weightInOz, String dateCaught, String locationLatitude, String locationLongitude) {           //Added String rating as a parameter
        // ---- Get a new database key for the vote
        String key = fishBase.child(FishDataTag).push().getKey();
        // ---- set up the fish object
        Fish newFish = new Fish(key,species,weightInOz,dateCaught,locationLatitude,locationLongitude);
        // ---- write the vote to Firebase
        fishBase.child(key).setValue(newFish);
        return newFish;
    }

    public void deleteFish(Fish fish) {
        // get key for item
        String key = fish.getKey();
        // set item to Null in database
        fishBase.child(key).removeValue();
    }

    public List<Fish> getAllFish(DataSnapshot dataSnapshot) {
        // instantiate ArrayList for return
        List<Fish> fishList = new ArrayList<Fish>();
        // iterate over snapshot to populate list
        for (DataSnapshot fishShot: dataSnapshot.getChildren()) {
            Fish addFish = fishShot.getValue(Fish.class);
            fishList.add(addFish);
        }
        return fishList;
    }

}
