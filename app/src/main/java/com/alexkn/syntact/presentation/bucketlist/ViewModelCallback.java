package com.alexkn.syntact.presentation.bucketlist;

import com.alexkn.syntact.data.model.Bucket;

interface ViewModelCallback {

    void delete(Bucket bucket);
}
