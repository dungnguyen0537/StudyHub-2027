package com.studyhub.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.studyhub.constant.FirestoreConstants;
import com.studyhub.model.User;

public class FirestoreHelper {

    private final FirebaseFirestore db;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> saveUser(User user) {
        return db.collection(FirestoreConstants.COLLECTION_USERS)
                .document(user.getUid())
                .set(user);
    }

    public Task<DocumentSnapshot> getUser(String uid) {
        return db.collection(FirestoreConstants.COLLECTION_USERS)
                .document(uid)
                .get();
    }
    
    public FirebaseFirestore getDb() {
        return db;
    }
}
